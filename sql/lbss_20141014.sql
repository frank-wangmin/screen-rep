-- handle the datas with the value of district_code is null --
update bss_panel set district_code='888888' where epg_panel_id is not null;

update bss_panel set district_code='100000' where epg_panel_id is null and district_code is null;

update bss_preview_template set district_code='100000' where district_code is null and epg_template_id is null;

update bss_preview_template set district_code='888888' where district_code is null and epg_template_id is not null;

update bss_panel_item set district_code='100000' where district_code ='10000' and epg_content_id is null;

update bss_panel_item set district_code='888888' where district_code is null and epg_content_id is not null;
