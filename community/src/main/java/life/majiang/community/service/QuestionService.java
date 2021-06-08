package life.majiang.community.service;

import life.majiang.community.dto.PaginationDTO;
import life.majiang.community.dto.QuestionDTO;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {              //联合mapper中的Mapper
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    public PaginationDTO ServiceList(Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totaPage;//最后一页页码

//        Integer totaCount = (int)questionMapper.countByExample(new QuestionExample());

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

//      List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(),new RowBounds(offset,size));
        List<Question> questions = questionMapper.MapperList(offset, size);
        Collections.reverse(questions);//倒叙
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
//          User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//快速的将question对象里的所有属性拷贝到questionDTO对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }//创建service的目的：可以同时使用mapper里的Mapper

    public PaginationDTO list(Long userId, Integer page, Integer size) {
        PaginationDTO paginationDTO = new PaginationDTO();

        Integer totaPage;//最后一页页码
//      QuestionExample questionExample = new QuestionExample();
//      questionExample.createCriteria()
//                   .andCreatorEqualTo(userId);
//      Integer totaCount = (int)questionMapper.countByExample(questionExample);
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
//      QuestionExample example = new QuestionExample();
//      example.createCriteria()
//             .andCreatorEqualTo(userId);
//      List<Question> questions = questionMapper.selectByExampleWithRowbounds(example,new RowBounds(offset,size));
        List<Question> questions = questionMapper.listByUserId(userId, offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<QuestionDTO>();

        for (Question question : questions) {
            User user = userMapper.findById(question.getCreator());
//          User user = userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);//快速的将question对象里的所有属性拷贝到questionDTO对象
            questionDTO.setUser(user);
            questionDTOList.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOList);

        return paginationDTO;
    }

    //获取question详情
    public QuestionDTO getById(Long id) {
//      Question question = questionMapper.selectByPrimaryKey(id);
        Question question = questionMapper.getById(id);
        if (question == null) {
            //为空有错,抛出异常,跳转error页面，显示信息。此时异常为通用问题,去CustomizeExceptionHandler抓取
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }//CustomizeErrorCode.QUESTION_NOT_FOUND去exception包里自己看
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        User user = userMapper.findById(question.getCreator());
//      User user = userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);

        return questionDTO;
    }

    public void createQrUpdate(Question question) {
        //判断是否为空，空就是第一次创建，不是就更新
        if (question.getId() == null) {
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCount(0);
            question.setLikeCount(0);
            question.setCommentCount(0);
//            questionMapper.insert(question);
            questionMapper.create(question);
        } else {
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
//            Question updateQuestion = new Question();
//            updateQuestion.setGmtModified(System.currentTimeMillis());
//            updateQuestion.setTitle(question.getTitle());
//            updateQuestion.setDescription(question.getDescription());
//            updateQuestion.setTag(question.getTag());
//            QuestionExample example = new QuestionExample();
//            example.createCriteria()
//                    .andIdEqualTo(question.getId());
//            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
//            if (updated != 1){
//                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
//            }
        }
    }

    public void incView(Long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
//        Question question = questionMapper.selectByPrimaryKey(id);
//        Question updateQuestion = new Question();
//        updateQuestion.setViewCount(question.getViewCount() + 1 );
//        QuestionExample questionExample = new QuestionExample();
//        //更新
//        questionExample.createCriteria()
//                       .andIdEqualTo(id);
//        questionMapper.updateByExampleSelective(updateQuestion,questionExample);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTo) {
        if (StringUtils.isBlank(queryDTo.getTag())) {         //判断是否为空
            return new ArrayList<>();
        }
        String[] tags = StringUtils.split(queryDTo.getTag(), ",");
        String regexpTag = Arrays.stream(tags).collect(Collectors.joining("|"));

        Question question = new Question();
        question.setId(queryDTo.getId());
        question.setTag(regexpTag);

        List<Question> questions = questionMapper.selectRelated(question);
        //questions转QuestionDTO
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);//赋值
            return questionDTO;
        }).collect(Collectors.toList());

        return questionDTOS;
    }
}