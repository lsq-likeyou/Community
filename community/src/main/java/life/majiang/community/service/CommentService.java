package life.majiang.community.service;

import life.majiang.community.dto.CommentDTO;
import life.majiang.community.enums.CommentTypeEnum;
import life.majiang.community.exception.CustomizeErrorCode;
import life.majiang.community.exception.CustomizeException;
import life.majiang.community.mapper.CommentMapper;
import life.majiang.community.mapper.QuestionExtMapper;
import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Comment;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;
    @Autowired
    private UserMapper userMapper;

    @Transactional                            //事务:当一条执行失败，整个方法失败。全成功则成功
    public void insert(Comment comment) {
        if (comment.getParentId() == null || comment.getParentId() == 0) {
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if (comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())) {
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if (comment.getType() == CommentTypeEnum.COMMENT.getType()) {
            //回复评论
//            Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
            Comment dbComment = commentMapper.ByParentId(comment.getParentId());
            if (dbComment == null) {
                throw new CustomizeException(CustomizeErrorCode.COMMENT_NOT_FOUND);
            }
            commentMapper.insert(comment);//否则成功
        } else {
            //回复问题
//            Question question = questionMapper.selectByPrimaryKey(comment.getParentId());
            Question question = questionMapper.getById(comment.getParentId());
            if (question == null) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert(comment);//否则成功
            question.setCommentCount(1);
            questionExtMapper.inCommentCount(question);
        }
    }

    public List<CommentDTO> listByQuestionId(Long id) {
//        CommentExample commentExample = new CommentExample();
//        commentExample.createCriteria()
//                      .andParentIdEqualTo(id)//当type等于不同情况的时候，才是问题下面的评论（二级？）
//                      .andTypeEqualTo(CommentTypeEnum.QUESTION.getType());//拿到所有comment（commentMapper？）
//        commentExample.setOrderByClause("gmt_create desc");//按照创建时间的倒叙排序
//        List<Comment> comments = commentMapper.selectByExample(commentExample);//去数据库查询，，，，拿到所有评论
        List<Comment> comments = commentMapper.listCommentator(id,1);//拿到所有一级评论人的id
        Collections.reverse(comments);//倒叙
        if (comments.size() == 0) {
            return new ArrayList<>();
        }
        //获取去重的评论人
        Set<Long> commentators = comments.stream().map(comment -> comment.getCommentator()).collect(Collectors.toSet());//拿到comments即可,获取到所有评论者id

        List<Long> userIds = new ArrayList<>();
        userIds.addAll(commentators);//将评论人id转化成使用者的id
        //获取评论人并转换为map
//        UserExample userExample = new UserExample();
//        userExample.createCriteria()
//                .andIdIn(userIds);
//        List<User> users = userMapper.selectByExample(userExample);//拿到所有user
        List<User> users = new ArrayList<>();
        for (Long userId : userIds) {
            User user = userMapper.findById(userId);
            if (user != null) {
                users.add(user);
            }
        }

        Map<Long, User> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> user));//将user写成map，方便输出
        //转换comment 为 commentDTO
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            commentDTO.setUser(userMap.get(comment.getCommentator()));
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}