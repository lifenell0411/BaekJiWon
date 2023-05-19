package com.KoreaIT.bjw._05_project.repository;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
@Mapper
public interface LikePointRepository {

	@Select("""
			<script>
				SELECT IFNULL(SUM(LP.point),0)
				FROM likePoint AS LP
				WHERE LP.relTypeCode = #{relTypeCode}
				AND LP.relId = #{id}
				AND LP.memberId = #{actorId}
			</script>
			""")
	public int getSumLikePointByMemberId(int actorId, String relTypeCode, int id);

	@Insert("""
			<script>
				INSERT INTO likePoint
				SET regDate = NOW(),
				updateDate = NOW(),
				relTypeCode = #{relTypeCode},
				relId = #{id},
				memberId = #{actorId},
				`point` = 1
			</script>
			""")
	public int addLikePoint(int actorId, String relTypeCode, int id);

 

	@Delete("""
			DELETE FROM likePoint
			WHERE relTypeCode = #{relTypeCode}
			AND relId = #{relId}
			AND memberId = #{actorId}
			""")
	public void deleteLikePoint(int actorId, String relTypeCode, int relId);
 
}
