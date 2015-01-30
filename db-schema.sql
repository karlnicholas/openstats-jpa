
    alter table DBAssembly_DBDistrict 
        drop constraint FK_ey9f1bmvegmm461v9h3w2t9wj;
    alter table DBAssembly_DBDistrict 
        drop constraint FK_fcbb20dk2hvm12m8mqmrq6gdy;
    alter table DBAssembly_groupInfoMap 
        drop constraint FK_aga9de8numrkiljshgnixjvqx;
    alter table DBAssembly_groupInfoMap 
        drop constraint FK_m14kccfdk6ekucm591i0kmgj3;
    alter table DBAssembly_groupInfoMap 
        drop constraint FK_e3x7ct2iyegt9w08j0vq8mlte;
    alter table DBAssembly_groupResultsMap 
        drop constraint FK_mu3dc10vl8i6g62eokcmufkds;
    alter table DBAssembly_groupResultsMap 
        drop constraint FK_cm2r0250hys0nelwneck4d4fx;
    alter table DBAssembly_groupResultsMap 
        drop constraint FK_q03btcrppy8am0oh1thm5tq71;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_neyks3k879m3g9wuqutimjdce;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_9xpiwbyuy1xjprewodxsf88cc;
    alter table DBDistrict_groupResultsMap 
        drop constraint FK_7yjdg1ekllqcem1msm68wygam;
    alter table DBDistrict_groupResultsMap 
        drop constraint FK_qpk1m5o4rqjeevamrfhmw3vtr;
    alter table DBDistrict_groupResultsMap 
        drop constraint FK_2sow4ye0m6t3llx3h1fh1pu5l;
    alter table DBGroupInfo_GroupItems 
        drop constraint FK_gf185iy4kvsjano69glgvg4ee;
    alter table DBGroupInfo_GroupItems 
        drop constraint FK_35kadui3bt9aqqgxhcwcmsbtb;
    alter table DBGroupResults_Results 
        drop constraint FK_aevegygxfhmq39x0f9rey4ldj;
    alter table DBLegislator_groupResultsMap 
        drop constraint FK_7nk0q9fot3ahn8g2hnny6gmvm;
    alter table DBLegislator_groupResultsMap 
        drop constraint FK_60gos6gbbj9lleb9152p5c07q;
    alter table DBLegislator_groupResultsMap 
        drop constraint FK_9vlkjla0bq7iv3j87acqaqxl6;
    drop table if exists DBAssembly cascade;
    drop table if exists DBAssembly_DBDistrict cascade;
    drop table if exists DBAssembly_groupInfoMap cascade;
    drop table if exists DBAssembly_groupResultsMap cascade;
    drop table if exists DBDistrict cascade;
    drop table if exists DBDistrict_DBLegislator cascade;
    drop table if exists DBDistrict_groupResultsMap cascade;
    drop table if exists DBGroup cascade;
    drop table if exists DBGroupInfo cascade;
    drop table if exists DBGroupInfo_GroupItems cascade;
    drop table if exists DBGroupResults cascade;
    drop table if exists DBGroupResults_Results cascade;
    drop table if exists DBInfoItem cascade;
    drop table if exists DBLegislator cascade;
    drop table if exists DBLegislator_groupResultsMap cascade;
    drop sequence hibernate_sequence;
    create table DBAssembly (
        id int8 not null,
        session varchar(255),
        state varchar(255),
        primary key (id)
    );
    create table DBAssembly_DBDistrict (
        DBAssembly_id int8 not null,
        districtList_id int8 not null
    );
    create table DBAssembly_groupInfoMap (
        DBAssembly int8 not null,
        DBGroupInfo int8 not null,
        DBGroup int8 not null,
        primary key (DBAssembly, DBGroup)
    );
    create table DBAssembly_groupResultsMap (
        DBAssembly int8 not null,
        DBGroupResults int8 not null,
        DBGroup int8 not null,
        primary key (DBAssembly, DBGroup)
    );
    create table DBDistrict (
        id int8 not null,
        chamber int4,
        description varchar(255),
        district varchar(3),
        primary key (id)
    );
    create table DBDistrict_DBLegislator (
        DBDistrict_id int8 not null,
        legislators_id int8 not null
    );
    create table DBDistrict_groupResultsMap (
        DBDistrict int8 not null,
        DBGroupResults int8 not null,
        DBGroup int8 not null,
        primary key (DBDistrict, DBGroup)
    );
    create table DBGroup (
        id int8 not null,
        groupDescription varchar(255),
        groupName varchar(255),
        primary key (id)
    );
    create table DBGroupInfo (
        id int8 not null,
        primary key (id)
    );
    create table DBGroupInfo_GroupItems (
        DBGroupInfo_id int8 not null,
        GroupItems_id int8 not null,
        GroupItems_ORDER int4 not null,
        primary key (DBGroupInfo_id, GroupItems_ORDER)
    );
    create table DBGroupResults (
        id int8 not null,
        primary key (id)
    );
    create table DBGroupResults_Results (
        DBGroupResults_id int8 not null,
        error varchar(255),
        value varchar(255),
        Results_ORDER int4 not null,
        primary key (DBGroupResults_id, Results_ORDER)
    );
    create table DBInfoItem (
        id int8 not null,
        Label varchar(255),
        description varchar(1023),
        primary key (id)
    );
    create table DBLegislator (
        id int8 not null,
        endDate date,
        name varchar(255),
        party varchar(255),
        startDate date,
        primary key (id)
    );
    create table DBLegislator_groupResultsMap (
        DBLegislator int8 not null,
        DBGroupResults int8 not null,
        DBGroup int8 not null,
        primary key (DBLegislator, DBGroup)
    );
    alter table DBAssembly_DBDistrict 
        add constraint UK_ey9f1bmvegmm461v9h3w2t9wj  unique (districtList_id);
    alter table DBAssembly_groupInfoMap 
        add constraint UK_aga9de8numrkiljshgnixjvqx  unique (DBGroupInfo);
    alter table DBAssembly_groupResultsMap 
        add constraint UK_mu3dc10vl8i6g62eokcmufkds  unique (DBGroupResults);
    alter table DBDistrict_DBLegislator 
        add constraint UK_neyks3k879m3g9wuqutimjdce  unique (legislators_id);
    alter table DBDistrict_groupResultsMap 
        add constraint UK_7yjdg1ekllqcem1msm68wygam  unique (DBGroupResults);
    alter table DBGroup 
        add constraint UK_fnj4ivbsm7v9e9b4q29g7k9vk  unique (groupName);
    alter table DBGroupInfo_GroupItems 
        add constraint UK_gf185iy4kvsjano69glgvg4ee  unique (GroupItems_id);
    alter table DBLegislator_groupResultsMap 
        add constraint UK_7nk0q9fot3ahn8g2hnny6gmvm  unique (DBGroupResults);
    alter table DBAssembly_DBDistrict 
        add constraint FK_ey9f1bmvegmm461v9h3w2t9wj 
        foreign key (districtList_id) 
        references DBDistrict;
    alter table DBAssembly_DBDistrict 
        add constraint FK_fcbb20dk2hvm12m8mqmrq6gdy 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    alter table DBAssembly_groupInfoMap 
        add constraint FK_aga9de8numrkiljshgnixjvqx 
        foreign key (DBGroupInfo) 
        references DBGroupInfo;
    alter table DBAssembly_groupInfoMap 
        add constraint FK_m14kccfdk6ekucm591i0kmgj3 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBAssembly_groupInfoMap 
        add constraint FK_e3x7ct2iyegt9w08j0vq8mlte 
        foreign key (DBAssembly) 
        references DBAssembly;
    alter table DBAssembly_groupResultsMap 
        add constraint FK_mu3dc10vl8i6g62eokcmufkds 
        foreign key (DBGroupResults) 
        references DBGroupResults;
    alter table DBAssembly_groupResultsMap 
        add constraint FK_cm2r0250hys0nelwneck4d4fx 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBAssembly_groupResultsMap 
        add constraint FK_q03btcrppy8am0oh1thm5tq71 
        foreign key (DBAssembly) 
        references DBAssembly;
    alter table DBDistrict_DBLegislator 
        add constraint FK_neyks3k879m3g9wuqutimjdce 
        foreign key (legislators_id) 
        references DBLegislator;
    alter table DBDistrict_DBLegislator 
        add constraint FK_9xpiwbyuy1xjprewodxsf88cc 
        foreign key (DBDistrict_id) 
        references DBDistrict;
    alter table DBDistrict_groupResultsMap 
        add constraint FK_7yjdg1ekllqcem1msm68wygam 
        foreign key (DBGroupResults) 
        references DBGroupResults;
    alter table DBDistrict_groupResultsMap 
        add constraint FK_qpk1m5o4rqjeevamrfhmw3vtr 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBDistrict_groupResultsMap 
        add constraint FK_2sow4ye0m6t3llx3h1fh1pu5l 
        foreign key (DBDistrict) 
        references DBDistrict;
    alter table DBGroupInfo_GroupItems 
        add constraint FK_gf185iy4kvsjano69glgvg4ee 
        foreign key (GroupItems_id) 
        references DBInfoItem;
    alter table DBGroupInfo_GroupItems 
        add constraint FK_35kadui3bt9aqqgxhcwcmsbtb 
        foreign key (DBGroupInfo_id) 
        references DBGroupInfo;
    alter table DBGroupResults_Results 
        add constraint FK_aevegygxfhmq39x0f9rey4ldj 
        foreign key (DBGroupResults_id) 
        references DBGroupResults;
    alter table DBLegislator_groupResultsMap 
        add constraint FK_7nk0q9fot3ahn8g2hnny6gmvm 
        foreign key (DBGroupResults) 
        references DBGroupResults;
    alter table DBLegislator_groupResultsMap 
        add constraint FK_60gos6gbbj9lleb9152p5c07q 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBLegislator_groupResultsMap 
        add constraint FK_9vlkjla0bq7iv3j87acqaqxl6 
        foreign key (DBLegislator) 
        references DBLegislator;
    create sequence hibernate_sequence;