use SPRING_TEST;

DROP FUNCTION if exists GET_WAREKI_CODE;

DELIMITER $$

CREATE FUNCTION GET_WAREKI_CODE(datein CHAR(8))
RETURNS CHAR(10)
DETERMINISTIC
READS SQL DATA
SQL SECURITY INVOKER
BEGIN
  DECLARE warekicd CHAR(10);
  
  select
    CODE into warekicd
  from
    co_m_gengou_info 
  where
    datein between START_DATE and END_DATE;

  RETURN concat(warekicd,datein);
  
END;
$$
/
DELIMITER ;
