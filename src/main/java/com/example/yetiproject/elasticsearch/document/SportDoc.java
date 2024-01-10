// package com.example.yetiproject.elasticsearch.document;
//
// import java.time.LocalDateTime;
//
// import org.springframework.data.annotation.Id;
// import org.springframework.data.elasticsearch.annotations.Document;
// import org.springframework.data.elasticsearch.annotations.Field;
// import org.springframework.data.elasticsearch.annotations.FieldType;
// import org.springframework.data.elasticsearch.annotations.Setting;
//
// import lombok.Data;
//
// @Data
// @Document(indexName = "sport")
// @Setting(replicas = 0)
// public class SportDoc {
//
// 	@Id
// 	private String id;
//
// 	@Field(name = "sport_id", type = FieldType.Long, index = false, docValues = false)
// 	private Long sportId;
//
// 	@Field(name = "stadium_name", type = FieldType.Text)
// 	private String stadiumName;
//
// 	@Field(name = "match_date", type = FieldType.Text)
// 	private String matchDate;
//
// 	@Field(name = "sport_name", type = FieldType.Text)
// 	private String sportName;
//
// }