package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {              //联合mapper中的Mapper
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO ServiceList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totaPage;//最后一页页码
        Integer totaCount = questionMapper.count();//拿到总页码
        if (totaCount % size == 0) {
            totaPage = totaCount / size;
        } else {
            totaPage = totaCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        } else if (page > totaPage) {
            page = totaPage;
        }
        paginationDTO.setPagination(totaPage, page);

        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.MapperList(offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//快速的将question对象里的所有属性拷贝到questionDTO对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }//创建service的目的：可以同时使用mapper里的Mapper

    public PaginationDTO list(Integer userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totaPage;//最后一页页码
        Integer totaCount = questionMapper.countByUserId(userId);//拿到总页码
        if (totaCount % size == 0) {
            totaPage = totaCount / size;
        } else {
            totaPage = totaCount / size + 1;
        }

        if (page < 1) {
            page = 1;
        } else if (page > totaPage) {
            page = totaPage;
        }
        paginationDTO.setPagination(totaPage, page);

        Integer offset = size * (page - 1);

        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//快速的将question对象里的所有属性拷贝到questionDTO对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }
}