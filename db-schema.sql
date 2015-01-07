
    alter table DBAssembly 
        drop constraint FK_d831tqk4156qmn972x3awocod;
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
    alter table DBDistricts_DBDistrict 
        drop constraint FK_2uiahvgobqsuby3loxlaaw7yb;
    alter table DBDistricts_DBDistrict 
        drop constraint FK_r81r07dube1quemr7r7nrgs7o;
    alter table DBDistricts_groupInfoMap 
        drop constraint FK_numbavcdpkvvdbiwli3tjxe6p;
    alter table DBDistricts_groupInfoMap 
        drop constraint FK_g1glympswmkh77fkalbpi5hj3;
    alter table DBDistricts_groupInfoMap 
        drop constraint FK_pioqet7669dpyn3w3nfggxmc2;
    alter table DBGroupInfo_aggregateGroupItems 
        drop constraint FK_52rpoabt3vj6s6tjkxlwwkoxb;
    alter table DBGroupInfo_aggregateGroupItems 
        drop constraint FK_ehd5nola9xk5vb157lep2usrn;
    alter table DBGroupInfo_computeGroupItems 
        drop constraint FK_42jfc2oocu9tqd0ke27nnns9g;
    alter table DBGroupInfo_computeGroupItems 
        drop constraint FK_8w7kdrfp0ky79kd7dvrbkrgf1;
    alter table DBGroupResults_aggregateResults 
        drop constraint FK_qatqy30c9w8iwpll7v5pq77yl;
    alter table DBGroupResults_computeResults 
        drop constraint FK_si44sqw6bc8rj4cu4oc0d39qt;
    drop table if exists DBAssembly cascade;
    drop table if exists DBAssembly_groupInfoMap cascade;
    drop table if exists DBAssembly_groupResultsMap cascade;
    drop table if exists DBDistrict cascade;
    drop table if exists DBDistrict_DBLegislator cascade;
    drop table if exists DBDistrict_groupResultsMap cascade;
    drop table if exists DBDistricts cascade;
    drop table if exists DBDistricts_DBDistrict cascade;
    drop table if exists DBDistricts_groupInfoMap cascade;
    drop table if exists DBGroup cascade;
    drop table if exists DBGroupInfo cascade;
    drop table if exists DBGroupInfo_aggregateGroupItems cascade;
    drop table if exists DBGroupInfo_computeGroupItems cascade;
    drop table if exists DBGroupResults cascade;
    drop table if exists DBGroupResults_aggregateResults cascade;
    drop table if exists DBGroupResults_computeResults cascade;
    drop table if exists DBInfoItem cascade;
    drop table if exists DBLegislator cascade;
    drop sequence hibernate_sequence;
    create table DBAssembly (
        id int8 not null,
        session varchar(255),
        state varchar(255),
        districts_id int8,
        primary key (id)
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
    create table DBDistricts (
        id int8 not null,
        primary key (id)
    );
    create table DBDistricts_DBDistrict (
        DBDistricts_id int8 not null,
        districtList_id int8 not null
    );
    create table DBDistricts_groupInfoMap (
        DBDistricts int8 not null,
        DBGroupInfo int8 not null,
        DBGroup int8 not null,
        primary key (DBDistricts, DBGroup)
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
    create table DBGroupInfo_aggregateGroupItems (
        DBGroupInfo_id int8 not null,
        aggregateGroupItems_id int8 not null,
        aggregateGroupItems_ORDER int4 not null,
        primary key (DBGroupInfo_id, aggregateGroupItems_ORDER)
    );
    create table DBGroupInfo_computeGroupItems (
        DBGroupInfo_id int8 not null,
        computeGroupItems_id int8 not null,
        computeGroupItems_ORDER int4 not null,
        primary key (DBGroupInfo_id, computeGroupItems_ORDER)
    );
    create table DBGroupResults (
        id int8 not null,
        primary key (id)
    );
    create table DBGroupResults_aggregateResults (
        DBGroupResults_id int8 not null,
        error int8 not null,
        value int8 not null,
        aggregateResults_ORDER int4 not null,
        primary key (DBGroupResults_id, aggregateResults_ORDER)
    );
    create table DBGroupResults_computeResults (
        DBGroupResults_id int8 not null,
        error float8 not null,
        value float8 not null,
        computeResults_ORDER int4 not null,
        primary key (DBGroupResults_id, computeResults_ORDER)
    );
    create table DBInfoItem (
        id int8 not null,
        Label varchar(255),
        description varchar(1023),
        primary key (id)
    );
    create table DBLegislator (
        id int8 not null,
        name varchar(255),
        party varchar(255),
        primary key (id)
    );
    alter table DBAssembly_groupInfoMap 
        add constraint UK_aga9de8numrkiljshgnixjvqx  unique (DBGroupInfo);
    alter table DBAssembly_groupResultsMap 
        add constraint UK_mu3dc10vl8i6g62eokcmufkds  unique (DBGroupResults);
    alter table DBDistrict_DBLegislator 
        add constraint UK_neyks3k879m3g9wuqutimjdce  unique (legislators_id);
    alter table DBDistrict_groupResultsMap 
        add constraint UK_7yjdg1ekllqcem1msm68wygam  unique (DBGroupResults);
    alter table DBDistricts_DBDistrict 
        add constraint UK_2uiahvgobqsuby3loxlaaw7yb  unique (districtList_id);
    alter table DBDistricts_groupInfoMap 
        add constraint UK_numbavcdpkvvdbiwli3tjxe6p  unique (DBGroupInfo);
    alter table DBGroup 
        add constraint UK_fnj4ivbsm7v9e9b4q29g7k9vk  unique (groupName);
    alter table DBGroupInfo_aggregateGroupItems 
        add constraint UK_52rpoabt3vj6s6tjkxlwwkoxb  unique (aggregateGroupItems_id);
    alter table DBGroupInfo_computeGroupItems 
        add constraint UK_42jfc2oocu9tqd0ke27nnns9g  unique (computeGroupItems_id);
    alter table DBAssembly 
        add constraint FK_d831tqk4156qmn972x3awocod 
        foreign key (districts_id) 
        references DBDistricts;
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
    alter table DBDistricts_DBDistrict 
        add constraint FK_2uiahvgobqsuby3loxlaaw7yb 
        foreign key (districtList_id) 
        references DBDistrict;
    alter table DBDistricts_DBDistrict 
        add constraint FK_r81r07dube1quemr7r7nrgs7o 
        foreign key (DBDistricts_id) 
        references DBDistricts;
    alter table DBDistricts_groupInfoMap 
        add constraint FK_numbavcdpkvvdbiwli3tjxe6p 
        foreign key (DBGroupInfo) 
        references DBGroupInfo;
    alter table DBDistricts_groupInfoMap 
        add constraint FK_g1glympswmkh77fkalbpi5hj3 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBDistricts_groupInfoMap 
        add constraint FK_pioqet7669dpyn3w3nfggxmc2 
        foreign key (DBDistricts) 
        references DBDistricts;
    alter table DBGroupInfo_aggregateGroupItems 
        add constraint FK_52rpoabt3vj6s6tjkxlwwkoxb 
        foreign key (aggregateGroupItems_id) 
        references DBInfoItem;
    alter table DBGroupInfo_aggregateGroupItems 
        add constraint FK_ehd5nola9xk5vb157lep2usrn 
        foreign key (DBGroupInfo_id) 
        references DBGroupInfo;
    alter table DBGroupInfo_computeGroupItems 
        add constraint FK_42jfc2oocu9tqd0ke27nnns9g 
        foreign key (computeGroupItems_id) 
        references DBInfoItem;
    alter table DBGroupInfo_computeGroupItems 
        add constraint FK_8w7kdrfp0ky79kd7dvrbkrgf1 
        foreign key (DBGroupInfo_id) 
        references DBGroupInfo;
    alter table DBGroupResults_aggregateResults 
        add constraint FK_qatqy30c9w8iwpll7v5pq77yl 
        foreign key (DBGroupResults_id) 
        references DBGroupResults;
    alter table DBGroupResults_computeResults 
        add constraint FK_si44sqw6bc8rj4cu4oc0d39qt 
        foreign key (DBGroupResults_id) 
        references DBGroupResults;
    create sequence hibernate_sequence;