package life.majiang.community.advice;

import life.majiang.community.exception.CustomizeException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

//拦截question有关的服务端异常
@ControllerAdvice
public class CustomizeExceptionHandler {

    @ExceptionHandler(Exception.class)
    ModelAndView handle(Throwable e, Model model) {
        if (e instanceof CustomizeException){
            model.addAttribute("message",e.getMessage());//拿到异常信息,显示页面,去exception包里获取异常信息
        }else{
            model.addAttribute("message","服务端问题,请稍等(初)");//error页面接收的信息,当异常处理不了时
        }
        return new ModelAndView("error");//返回定义的error页面
    }
}
