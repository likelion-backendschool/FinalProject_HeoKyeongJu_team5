package com.mutbook.week2_mission.app.domain.post.entity;

import com.mutbook.week2_mission.app.base.entity.BaseEntity;
import com.mutbook.week2_mission.app.domain.member.entity.Member;
import com.mutbook.week2_mission.app.domain.postTag.entity.PostTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
@Table(name = "post")
public class Post extends BaseEntity {
    @ManyToOne(fetch = LAZY)
    private Member author;
    @Column(name = "subject")
    private String subject;
    @Column(name = "content",columnDefinition = "LONGTEXT")
    private String content;
    @Column(name = "content_html",columnDefinition = "LONGTEXT")
    private String contentHtml;

    public String getForPrintContentHtml() {
        return contentHtml.replaceAll("toastui-editor-ww-code-block-highlighting", "");
    }
    public String getExtra_inputValue_hashTagContents() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("postTags") == false) {
            return "";
        }

        List<PostTag> postTags = (List<PostTag>) extra.get("postTags");

        if (postTags.isEmpty()) {
            return "";
        }

        return postTags
                .stream()
                .map(postTag -> "#" + postTag.getPostKeyword().getContent())
                .sorted()
                .collect(Collectors.joining(" "));
    }

    public String getExtra_postTagLinks() {
        Map<String, Object> extra = getExtra();

        if (extra.containsKey("postTags") == false) {
            return "";
        }

        List<PostTag> postTags = (List<PostTag>) extra.get("postTags");

        if (postTags.isEmpty()) {
            return "";
        }

        return postTags
                .stream()
                .map(postTag -> {
                    String text = "#" + postTag.getPostKeyword().getContent();

                    return """
                            <a href="%s" class="text-link">%s</a>
                            """
                            .stripIndent()
                            .formatted(postTag.getPostKeyword().getListUrl(), text);
                })
                .sorted()
                .collect(Collectors.joining(" "));
    }

}
