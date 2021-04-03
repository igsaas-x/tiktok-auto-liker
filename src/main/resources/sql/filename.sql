CREATE TRIGGER `file_entity_before_insert` BEFORE INSERT ON `file_entity` FOR EACH ROW
BEGIN
   if((select 1 from file_entity where name = new.name) > 0) then
      set new.name = Concat(Replace(new.name,Concat('.',new.extension),''),'(1)',Concat('.',new.extension));
   end if;
END