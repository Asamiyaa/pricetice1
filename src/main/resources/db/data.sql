insert into  inv_apply(id,fpqqlsh,status_code,einvoice_code,einvoice_number,random_Number,issue_date,total_amount,handling_person,payer_party_name)
values(1,'12345678901234567891','4','12345678','12345678','sdlkfj',now(),100.00,'开票人','交款人名称');
insert into inv_apply_b(hid,item_code,/*item_name,*/item_amount,item_unit,item_quantity,item_std,create_time,ts)
values  (1    ,'2390',      /*'加油计划', */   34.00,       '元',                     1,       100,now(),now());