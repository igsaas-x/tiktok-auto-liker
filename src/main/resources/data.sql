insert into permission(id,version,action_name,entity_name,code_name) values (1,0,'all','all','all_all');
insert into permission(id,version,action_name,entity_name,code_name) values (2,0,'read','all','read_all');
insert into permission(id,version,action_name,entity_name,code_name) values (10,0,'create','appuser','create_appuser');

insert into user_role(id,`version`,name) values (1,0,'admin');

insert into user_role_permissions values (1,1);

insert into users(id,`version`,user_name,email,`password`,status,role_id) values (1,0,'admin','admin@mail.com','$2a$10$BCcz5D3246cIiRbaaNzDiO5pz4.hMYNl5/YHFo8sYkECLZUGmBo22','ACTIVE',1);