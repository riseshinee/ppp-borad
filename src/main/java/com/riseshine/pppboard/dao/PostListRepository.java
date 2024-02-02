package com.riseshine.pppboard.dao;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import com.riseshine.pppboard.domain.Post;
import static com.riseshine.pppboard.domain.QPost.post;

@Repository
public class PostListRepository {

  private final EntityManager em;
  private final JPAQueryFactory query;

  public PostListRepository(EntityManager em) {
    this.em = em;
    this.query = new JPAQueryFactory(em);
  }

  /**
   * 게시글 검색 및 리스트 조회 query
   * @param title
   * @param content
   * @param pageable
   * @return
   */
  public Page<Post> getList(String title, String content, Pageable pageable) {
    QueryResults<Post> results = query
            .selectFrom(post)
            .where(post.deletedAt.isNull()
                    .and(post.title.contains(title))
                    .and(post.content.contains(content))
            )
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .orderBy(post.no.desc())
            .fetchResults();

    List<Post> data = results.getResults();
    long total = results.getTotal();
    return new PageImpl<>(data,pageable,total);
  }
}
