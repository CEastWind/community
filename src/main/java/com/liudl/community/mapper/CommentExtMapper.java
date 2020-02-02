package com.liudl.community.mapper;

import com.liudl.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment record);
}