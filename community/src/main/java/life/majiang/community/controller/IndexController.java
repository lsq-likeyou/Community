package life.majiang.community.controller;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    //检验登录状态
    public String index( //HttpServletRequest request,
                         Model model,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "6") Integer size) {//page,size获取页码,传入list()
//代码行在SessionInterceptor类中，写的一个拦截器，在此类中恢复需要注入UserMapper
        //查询发布的数据 列表
        PaginationDTO pagination = questionService.ServiceList(page, size);
        model.addAttribute("pagination", pagination);
        return "index";
    }
}