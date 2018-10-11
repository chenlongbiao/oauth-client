package com.oauth.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author chenlongbiao
 * @version V1.0
 * @Title:
 * @date 2018/10/9
 */
@RestController
@Slf4j
public class TestController {

    @RequestMapping(method = RequestMethod.GET,value = "/test")
    public String test(){
        return "123";
    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        int i = 0;

        Date time = new Date();
        try {
            while (true) {
//                log.info(i+ "");
                list.add(i++);
                list.add(i++);
                list.add(i++);
                list.add(i++);
                list.add(i++);
                list.add(i++);
                list.add(i++);
                Long i1 = new Date().getTime() - time.getTime();
                log.info(i1 + "");
                log.info(i + "");
            }

        }catch (Exception e){

        }
    }
}
