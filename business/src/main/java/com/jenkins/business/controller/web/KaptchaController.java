package com.jenkins.business.controller.web;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.concurrent.TimeUnit;

/**
 * @author JenkinsZhang
 * @date 2020/8/17
 */

@RestController("webKaptchaController")
@RequestMapping("/web/kaptcha")
public class KaptchaController {

    public static final String BUSINESS_NAME = "Web Kaptcha";

    @Autowired
    @Qualifier("getWebKaptcha")
    DefaultKaptcha defaultKaptcha;

    @Autowired
    RedisTemplate redisTemplate;
    @GetMapping("/image-code/{imageToken}")
    public void imageCode(@PathVariable(value = "imageToken") String imageToken, HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception{
        ByteArrayOutputStream jpegOutputStream = new ByteArrayOutputStream();
        try {
            // generate kaptcha text;
            String createText = defaultKaptcha.createText();

            //add token to redis
//             request.getSession().setAttribute(imageCodeToken, createText);
            redisTemplate.opsForValue().set(imageToken,createText,300, TimeUnit.SECONDS);
            // add token to redis
//            redisTemplate.opsForValue().set(imageCodeToken, createText, 300, TimeUnit.SECONDS);

            BufferedImage challenge = defaultKaptcha.createImage(createText);
            ImageIO.write(challenge, "jpg", jpegOutputStream);
        } catch (IllegalArgumentException e) {
            httpServletResponse.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // output as an image
        byte[] captchaChallengeAsJpeg = jpegOutputStream.toByteArray();
        httpServletResponse.setHeader("Cache-Control", "no-store");
        httpServletResponse.setHeader("Pragma", "no-cache");
        httpServletResponse.setDateHeader("Expires", 0);
        httpServletResponse.setContentType("image/jpeg");
        ServletOutputStream responseOutputStream = httpServletResponse.getOutputStream();
        responseOutputStream.write(captchaChallengeAsJpeg);
        responseOutputStream.flush();
        responseOutputStream.close();
    }
}
