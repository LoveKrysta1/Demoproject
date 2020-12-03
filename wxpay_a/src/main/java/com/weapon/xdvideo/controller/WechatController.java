package com.weapon.xdvideo.controller;

import com.weapon.xdvideo.config.WeChatConfig;
import com.weapon.xdvideo.domain.User;
import com.weapon.xdvideo.domain.VideoOrder;
import com.weapon.xdvideo.service.UserService;
import com.weapon.xdvideo.service.VideoOrderService;
import com.weapon.xdvideo.utils.JsonData;
import com.weapon.xdvideo.utils.JwtUtils;
import com.weapon.xdvideo.utils.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import java.util.SortedMap;

@Controller
@RequestMapping("/api/v1/wechat")
public class WechatController {

    @Autowired
    private WeChatConfig weChatConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 拼装wechat扫一扫登录url
     * @return
     */
    //这里qrcode后就重定向到/user/callback那里去了
    @GetMapping("login_url")
    @ResponseBody
    public JsonData loginUrl(@RequestParam(value = "access_page",required = true)String accessPage) throws UnsupportedEncodingException {

        String redirectUrl = weChatConfig.getOpenRedirectUrl(); //获取开放平台重定向地址

        String callbackUrl = URLEncoder.encode(redirectUrl,"GBK"); //对重定向地址进行编码

        String qrcodeUrl = String.format(weChatConfig.getOpenQrcodeUrl(),weChatConfig.getOpenAppid(),callbackUrl,accessPage);

        return JsonData.buildSuccess(qrcodeUrl);
    }


    /**
     * 微信扫码登录，回调地址，方法
     * @param code
     * @param state
     * @param response
     * @throws IOException
     */
    @GetMapping("/user/callback")//state是当前页面，为了回调到当前页面，这样对用户的体验十分好,response重定向
    public void wechatUserCallback(@RequestParam(value="code",required = true) String code,
                                   String state, HttpServletResponse response) throws IOException {
        //测试
//        System.out.println("code=" + code);
//        System.out.println("state=" + state);
        //通过code获取access_token
        User user = userService.saveWeChatUser(code);
        if(user != null){
            //生成jwt
            String token = JwtUtils.geneJsonWebToken(user);//解决name URL乱码问题
            // state 当前用户的页面地址，需要拼接 Http:// 这样才不会站内跳转
            response.sendRedirect(state+"?token="+token+"&head_img="+user.getHeadImg()+"&name="+URLEncoder.encode(user.getName(),"UTF-8"));
        }
    }

    /**
     * 微信支付回调
     */

    @RequestMapping("/order/callback")
    public void orderCallback(HttpServletRequest request,HttpServletResponse response) throws Exception {

        InputStream inputStream = request.getInputStream();
        //BufferedReader 是包装设计模式，性能更高
        BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuffer sb = new StringBuffer();
        String line;
        while((line = in.readLine()) != null){
            sb.append(line);
        }
        in.close();
        inputStream.close();

        Map<String,String> callbackMap = WXPayUtil.xmlToMap(sb.toString());
        System.out.println(callbackMap.toString());
        SortedMap<String, String> sortedMap = WXPayUtil.getSortedMap(callbackMap);

        //判读签名是否正确
        if(WXPayUtil.isCorrectSign(sortedMap,weChatConfig.getKey())){

            if("SUCCESS".equals(sortedMap.get("result_code"))){
                String outTradeNo = sortedMap.get("out_trade_no");
                VideoOrder byOutTradeNo = videoOrderService.findByOutTradeNo(outTradeNo);
                //更新字段 更新订单状态
                if(byOutTradeNo !=null && byOutTradeNo.getState() ==0){//判断逻辑看业务场景
                    VideoOrder videoOrder = new VideoOrder();
                    videoOrder.setState(1);
                    videoOrder.setOpenid(sortedMap.get("openid"));
                    videoOrder.setOutTradeNo(outTradeNo);
                    videoOrder.setNotifyTime(new Date());
                    int rows = videoOrderService.updateVideoOrderByOutTradeNo(videoOrder);
                    //影响行数row ==1 或者row==0 响应微信成功或者失败
                    if(rows == 1){//通知微信订单处理成功
                        response.setContentType("text/xml");
                        response.getWriter().print("success");
                        return;//下面不执行了
                    }
                }
            }
        }

        //都处理失败
        response.setContentType("text/xml");
        response.getWriter().print("fail");



    }
}
