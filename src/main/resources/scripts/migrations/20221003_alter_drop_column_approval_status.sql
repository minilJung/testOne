
# 교육, 수상 approvalStatus 컬럼 삭제
alter table user_education drop column status;
alter table user_rwps drop column status;
