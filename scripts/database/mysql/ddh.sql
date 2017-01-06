DELIMITER //  
 CREATE PROCEDURE ddh(in types TINYINT,OUT ddh_return BIGINT)  
  BEGIN 
  SELECT counts INTO ddh_return from t_advisory_seeds where TYPE=types;
  UPDATE t_advisory_seeds set COUNTS=COUNTS+1 where type=types;
  END 
   //  
 DELIMITER ; 