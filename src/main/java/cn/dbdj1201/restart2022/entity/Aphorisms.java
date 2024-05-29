package cn.dbdj1201.restart2022.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

/**
 * @Author: yz1201
 * @Date: 2023/11/10 14:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("t_aphorisms")
public class Aphorisms {

    /*
    {
  "_id": "CPwqIPvFW_",
  "content": "I define friendship as a bond that transcends all barriers. When you are ready to expect anything and everything from friends, good, bad or ugly... that's what I call true friendship.",
  "author": "Harbhajan Singh",
  "tags": [
    "Friendship"
  ],
  "authorSlug": "harbhajan-singh",
  "length": 183,
  "dateAdded": "2019-02-21",
  "dateModified": "2023-04-14"
}
     */
    @TableId("id")
    private String _id;
    private String content;
    private String author;
    private List<String> tags;
    private Integer length;
    private String authorSlug;
    private LocalDate dateAdded;
    private LocalDate dateModified;
}
