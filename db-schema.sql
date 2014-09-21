
    alter table District_Legislator 
        drop constraint FK_2uffod2ldeb2cv0yvsf750siv;
    alter table District_Legislator 
        drop constraint FK_5pm9sjbqss6dnn64r4sraenc3;
    alter table District_aggregates 
        drop constraint FK_f8bjk7728cgug743jif4pneiy;
    alter table District_computations 
        drop constraint FK_ksro43goqx3hq1uggsfnkcs1h;
    alter table Districts_District 
        drop constraint FK_fiygsso958eeod0puoi09e83n;
    alter table Districts_District 
        drop constraint FK_hdk54mjpm5y8tuwj1crse9oqe;
    alter table GroupInfo_groupDescriptions 
        drop constraint FK_h1e036v12ho97fe283d63jkkf;
    alter table GroupInfo_groupLabels 
        drop constraint FK_dksf6752wqb8v0s2tw6wnml9o;
    alter table Session 
        drop constraint FK_da03b1jhsycp684r2x3ipja5v;
    alter table Session_aggregates 
        drop constraint FK_ambr5rtyfpa1p177f1pxq9sl;
    alter table Session_computations 
        drop constraint FK_svfno1kf3n2w4g7yhtv3nlm0m;
    alter table districts_aggregategroupmap 
        drop constraint FK_mi5hp56igll0b9vrfm72wnje;
    alter table districts_aggregategroupmap 
        drop constraint FK_4rrfe3663pn4bp9k5k6fvg8ap;
    alter table districts_computationgroupmap 
        drop constraint FK_g6mwljbhrpt40bac4xomj34e7;
    alter table districts_computationgroupmap 
        drop constraint FK_idg2fsr5m4nbcxt146jasnb8k;
    alter table session_aggregategroupmap 
        drop constraint FK_o2ykghh9xyuyayugsjbhhhohp;
    alter table session_aggregategroupmap 
        drop constraint FK_f7w58rb7hkgo41pg0sk6eltxw;
    alter table session_computationgroupmap 
        drop constraint FK_55nn15qy8qd0qrhtpaeat7vnb;
    alter table session_computationgroupmap 
        drop constraint FK_68mmmkyslpo1nmc6qu969tye0;
    drop table if exists District cascade;
    drop table if exists District_Legislator cascade;
    drop table if exists District_aggregates cascade;
    drop table if exists District_computations cascade;
    drop table if exists Districts cascade;
    drop table if exists Districts_District cascade;
    drop table if exists GroupInfo cascade;
    drop table if exists GroupInfo_groupDescriptions cascade;
    drop table if exists GroupInfo_groupLabels cascade;
    drop table if exists Legislator cascade;
    drop table if exists Session cascade;
    drop table if exists Session_aggregates cascade;
    drop table if exists Session_computations cascade;
    drop table if exists districts_aggregategroupmap cascade;
    drop table if exists districts_computationgroupmap cascade;
    drop table if exists session_aggregategroupmap cascade;
    drop table if exists session_computationgroupmap cascade;
    drop sequence hibernate_sequence;
    create table District (
        id int8 not null,
        chamber varchar(255),
        district varchar(255),
        primary key (id)
    );
    create table District_Legislator (
        District_id int8 not null,
        legislators_id int8 not null
    );
    create table District_aggregates (
        District_id int8 not null,
        aggregates bytea,
        aggregates_KEY varchar(255),
        primary key (District_id, aggregates_KEY)
    );
    create table District_computations (
        District_id int8 not null,
        computations bytea,
        computations_KEY varchar(255),
        primary key (District_id, computations_KEY)
    );
    create table Districts (
        id int8 not null,
        primary key (id)
    );
    create table Districts_District (
        Districts_id int8 not null,
        districtList_id int8 not null
    );
    create table GroupInfo (
        id int8 not null,
        primary key (id)
    );
    create table GroupInfo_groupDescriptions (
        GroupInfo_id int8 not null,
        groupDescriptions varchar(255),
        groupDescriptions_ORDER int4 not null,
        primary key (GroupInfo_id, groupDescriptions_ORDER)
    );
    create table GroupInfo_groupLabels (
        GroupInfo_id int8 not null,
        groupLabels varchar(255),
        groupLabels_ORDER int4 not null,
        primary key (GroupInfo_id, groupLabels_ORDER)
    );
    create table Legislator (
        id int8 not null,
        name varchar(255),
        party varchar(255),
        primary key (id)
    );
    create table Session (
        id int8 not null,
        session varchar(255),
        state varchar(255),
        districts_id int8,
        primary key (id)
    );
    create table Session_aggregates (
        Session_id int8 not null,
        aggregates bytea,
        aggregates_KEY varchar(255),
        primary key (Session_id, aggregates_KEY)
    );
    create table Session_computations (
        Session_id int8 not null,
        computations bytea,
        computations_KEY varchar(255),
        primary key (Session_id, computations_KEY)
    );
    create table districts_aggregategroupmap (
        Districts_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY varchar(255),
        primary key (Districts_id, aggregateGroupMap_KEY)
    );
    create table districts_computationgroupmap (
        Districts_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY varchar(255),
        primary key (Districts_id, computationGroupMap_KEY)
    );
    create table session_aggregategroupmap (
        Session_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY varchar(255),
        primary key (Session_id, aggregateGroupMap_KEY)
    );
    create table session_computationgroupmap (
        Session_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY varchar(255),
        primary key (Session_id, computationGroupMap_KEY)
    );
    alter table District_Legislator 
        add constraint UK_2uffod2ldeb2cv0yvsf750siv unique (legislators_id);
    alter table Districts_District 
        add constraint UK_fiygsso958eeod0puoi09e83n unique (districtList_id);
    alter table districts_aggregategroupmap 
        add constraint UK_mi5hp56igll0b9vrfm72wnje unique (aggregateGroupMap_id);
    alter table districts_computationgroupmap 
        add constraint UK_g6mwljbhrpt40bac4xomj34e7 unique (computationGroupMap_id);
    alter table session_aggregategroupmap 
        add constraint UK_o2ykghh9xyuyayugsjbhhhohp unique (aggregateGroupMap_id);
    alter table session_computationgroupmap 
        add constraint UK_55nn15qy8qd0qrhtpaeat7vnb unique (computationGroupMap_id);
    alter table District_Legislator 
        add constraint FK_2uffod2ldeb2cv0yvsf750siv 
        foreign key (legislators_id) 
        references Legislator;
    alter table District_Legislator 
        add constraint FK_5pm9sjbqss6dnn64r4sraenc3 
        foreign key (District_id) 
        references District;
    alter table District_aggregates 
        add constraint FK_f8bjk7728cgug743jif4pneiy 
        foreign key (District_id) 
        references District;
    alter table District_computations 
        add constraint FK_ksro43goqx3hq1uggsfnkcs1h 
        foreign key (District_id) 
        references District;
    alter table Districts_District 
        add constraint FK_fiygsso958eeod0puoi09e83n 
        foreign key (districtList_id) 
        references District;
    alter table Districts_District 
        add constraint FK_hdk54mjpm5y8tuwj1crse9oqe 
        foreign key (Districts_id) 
        references Districts;
    alter table GroupInfo_groupDescriptions 
        add constraint FK_h1e036v12ho97fe283d63jkkf 
        foreign key (GroupInfo_id) 
        references GroupInfo;
    alter table GroupInfo_groupLabels 
        add constraint FK_dksf6752wqb8v0s2tw6wnml9o 
        foreign key (GroupInfo_id) 
        references GroupInfo;
    alter table Session 
        add constraint FK_da03b1jhsycp684r2x3ipja5v 
        foreign key (districts_id) 
        references Districts;
    alter table Session_aggregates 
        add constraint FK_ambr5rtyfpa1p177f1pxq9sl 
        foreign key (Session_id) 
        references Session;
    alter table Session_computations 
        add constraint FK_svfno1kf3n2w4g7yhtv3nlm0m 
        foreign key (Session_id) 
        references Session;
    alter table districts_aggregategroupmap 
        add constraint FK_mi5hp56igll0b9vrfm72wnje 
        foreign key (aggregateGroupMap_id) 
        references GroupInfo;
    alter table districts_aggregategroupmap 
        add constraint FK_4rrfe3663pn4bp9k5k6fvg8ap 
        foreign key (Districts_id) 
        references Districts;
    alter table districts_computationgroupmap 
        add constraint FK_g6mwljbhrpt40bac4xomj34e7 
        foreign key (computationGroupMap_id) 
        references GroupInfo;
    alter table districts_computationgroupmap 
        add constraint FK_idg2fsr5m4nbcxt146jasnb8k 
        foreign key (Districts_id) 
        references Districts;
    alter table session_aggregategroupmap 
        add constraint FK_o2ykghh9xyuyayugsjbhhhohp 
        foreign key (aggregateGroupMap_id) 
        references GroupInfo;
    alter table session_aggregategroupmap 
        add constraint FK_f7w58rb7hkgo41pg0sk6eltxw 
        foreign key (Session_id) 
        references Session;
    alter table session_computationgroupmap 
        add constraint FK_55nn15qy8qd0qrhtpaeat7vnb 
        foreign key (computationGroupMap_id) 
        references GroupInfo;
    alter table session_computationgroupmap 
        add constraint FK_68mmmkyslpo1nmc6qu969tye0 
        foreign key (Session_id) 
        references Session;
    create sequence hibernate_sequence;