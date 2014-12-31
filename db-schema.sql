
    alter table AggregateResults_resultList 
        drop constraint FK_ffnfmudk8fof9dkp3sm5mmbhy;
    alter table ComputationResults_resultList 
        drop constraint FK_rxs52u3j9fwii6k76hptdo3oy;
    alter table DBAssembly 
        drop constraint FK_d831tqk4156qmn972x3awocod;
    alter table DBAssembly_AggregateResults 
        drop constraint FK_gfrlbw26jewxxfr9luue09xfr;
    alter table DBAssembly_AggregateResults 
        drop constraint FK_fj9dcei5o94ejf7eea5r39chs;
    alter table DBAssembly_AggregateResults 
        drop constraint FK_dx6mg673nhtsad5w72mefxbgj;
    alter table DBAssembly_ComputationResults 
        drop constraint FK_g8o5yrs20a7rkmgjjjihl181n;
    alter table DBAssembly_ComputationResults 
        drop constraint FK_2pn9fu0f9ploaw7ttjstb5yy0;
    alter table DBAssembly_ComputationResults 
        drop constraint FK_e28i1c09ykq4bgnjwlm7j5jp0;
    alter table DBDistrict_AggregateResults 
        drop constraint FK_lhbqs8saukjfjvm34v3dmhpst;
    alter table DBDistrict_AggregateResults 
        drop constraint FK_s09h9udp4ll5vu2y7e6d9vl8u;
    alter table DBDistrict_AggregateResults 
        drop constraint FK_61j3bhtkqq5sm1exn8lvhcueh;
    alter table DBDistrict_ComputationResults 
        drop constraint FK_g8cavqboqvpss7f3i2uy4ivmi;
    alter table DBDistrict_ComputationResults 
        drop constraint FK_4h91nt33r0x0bv2nkf78c89sj;
    alter table DBDistrict_ComputationResults 
        drop constraint FK_bq8n3dotxfuxnw2vw4qqd5swa;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_neyks3k879m3g9wuqutimjdce;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_9xpiwbyuy1xjprewodxsf88cc;
    alter table DBDistricts_DBDistrict 
        drop constraint FK_2uiahvgobqsuby3loxlaaw7yb;
    alter table DBDistricts_DBDistrict 
        drop constraint FK_r81r07dube1quemr7r7nrgs7o;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_8591nauyvlo84o69e4af265wh;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_kwjleaqh3r5n2wpdv10ln3sbx;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_2vht55myh4li65ppwblhk0rc2;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_j4hurmg0j2bu09y48uy4jmbik;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_438t43u5g59cwbut6fxohjm1r;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_epe6ngy0pw6dqbfbx0gj9f2nd;
    alter table DBGroupInfo_DBInfoItem 
        drop constraint FK_gk2n11lfx77klw8ndyxy4mg8j;
    alter table DBGroupInfo_DBInfoItem 
        drop constraint FK_e9e3y0koemwc0yg16s1f1xpkw;
    alter table assembly_aggregategroupmap 
        drop constraint FK_9poknpmgasyyp1obdt5qoe5le;
    alter table assembly_aggregategroupmap 
        drop constraint FK_qqslcx0jhkp9ueo9faajjhwlr;
    alter table assembly_aggregategroupmap 
        drop constraint FK_8p8bot46e5uka0bxr9cg5181s;
    alter table assembly_computationgroupmap 
        drop constraint FK_ob68eulskxrjds0u3vhe0u84b;
    alter table assembly_computationgroupmap 
        drop constraint FK_a7betyowd17b66w8rjiq36ypv;
    alter table assembly_computationgroupmap 
        drop constraint FK_phl554wenadqr28f1y8yhksp3;
    drop table if exists AggregateResults cascade;
    drop table if exists AggregateResults_resultList cascade;
    drop table if exists ComputationResults cascade;
    drop table if exists ComputationResults_resultList cascade;
    drop table if exists DBAssembly cascade;
    drop table if exists DBAssembly_AggregateResults cascade;
    drop table if exists DBAssembly_ComputationResults cascade;
    drop table if exists DBDistrict cascade;
    drop table if exists DBDistrict_AggregateResults cascade;
    drop table if exists DBDistrict_ComputationResults cascade;
    drop table if exists DBDistrict_DBLegislator cascade;
    drop table if exists DBDistricts cascade;
    drop table if exists DBDistricts_DBDistrict cascade;
    drop table if exists DBDistricts_aggregateGroupMap cascade;
    drop table if exists DBDistricts_computationGroupMap cascade;
    drop table if exists DBGroup cascade;
    drop table if exists DBGroupInfo cascade;
    drop table if exists DBGroupInfo_DBInfoItem cascade;
    drop table if exists DBInfoItem cascade;
    drop table if exists DBLegislator cascade;
    drop table if exists assembly_aggregategroupmap cascade;
    drop table if exists assembly_computationgroupmap cascade;
    drop sequence hibernate_sequence;
    create table AggregateResults (
        id int8 not null,
        primary key (id)
    );
    create table AggregateResults_resultList (
        AggregateResults_id int8 not null,
        error int8 not null,
        value int8 not null,
        resultList_ORDER int4 not null,
        primary key (AggregateResults_id, resultList_ORDER)
    );
    create table ComputationResults (
        id int8 not null,
        primary key (id)
    );
    create table ComputationResults_resultList (
        ComputationResults_id int8 not null,
        error float8 not null,
        value float8 not null,
        resultList_ORDER int4 not null,
        primary key (ComputationResults_id, resultList_ORDER)
    );
    create table DBAssembly (
        id int8 not null,
        session varchar(255),
        state varchar(255),
        districts_id int8,
        primary key (id)
    );
    create table DBAssembly_AggregateResults (
        DBAssembly_id int8 not null,
        aggregateMap_id int8 not null,
        aggregateMap_KEY int8 not null,
        primary key (DBAssembly_id, aggregateMap_KEY)
    );
    create table DBAssembly_ComputationResults (
        DBAssembly_id int8 not null,
        computationMap_id int8 not null,
        computationMap_KEY int8 not null,
        primary key (DBAssembly_id, computationMap_KEY)
    );
    create table DBDistrict (
        id int8 not null,
        chamber int4,
        description varchar(255),
        district varchar(3),
        primary key (id)
    );
    create table DBDistrict_AggregateResults (
        DBDistrict_id int8 not null,
        aggregateMap_id int8 not null,
        aggregateMap_KEY int8 not null,
        primary key (DBDistrict_id, aggregateMap_KEY)
    );
    create table DBDistrict_ComputationResults (
        DBDistrict_id int8 not null,
        computationMap_id int8 not null,
        computationMap_KEY int8 not null,
        primary key (DBDistrict_id, computationMap_KEY)
    );
    create table DBDistrict_DBLegislator (
        DBDistrict_id int8 not null,
        legislators_id int8 not null
    );
    create table DBDistricts (
        id int8 not null,
        primary key (id)
    );
    create table DBDistricts_DBDistrict (
        DBDistricts_id int8 not null,
        districtList_id int8 not null
    );
    create table DBDistricts_aggregateGroupMap (
        DBDistricts_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY int8 not null,
        primary key (DBDistricts_id, aggregateGroupMap_KEY)
    );
    create table DBDistricts_computationGroupMap (
        DBDistricts_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY int8 not null,
        primary key (DBDistricts_id, computationGroupMap_KEY)
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
    create table DBGroupInfo_DBInfoItem (
        DBGroupInfo_id int8 not null,
        groupItems_id int8 not null,
        groupItems_ORDER int4 not null,
        primary key (DBGroupInfo_id, groupItems_ORDER)
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
    create table assembly_aggregategroupmap (
        DBAssembly_id int8 not null,
        aggregateGroupMap_id int8 not null,
        aggregateGroupMap_KEY int8 not null,
        primary key (DBAssembly_id, aggregateGroupMap_KEY)
    );
    create table assembly_computationgroupmap (
        DBAssembly_id int8 not null,
        computationGroupMap_id int8 not null,
        computationGroupMap_KEY int8 not null,
        primary key (DBAssembly_id, computationGroupMap_KEY)
    );
    alter table DBAssembly_AggregateResults 
        add constraint UK_gfrlbw26jewxxfr9luue09xfr  unique (aggregateMap_id);
    alter table DBAssembly_ComputationResults 
        add constraint UK_g8o5yrs20a7rkmgjjjihl181n  unique (computationMap_id);
    alter table DBDistrict_AggregateResults 
        add constraint UK_lhbqs8saukjfjvm34v3dmhpst  unique (aggregateMap_id);
    alter table DBDistrict_ComputationResults 
        add constraint UK_g8cavqboqvpss7f3i2uy4ivmi  unique (computationMap_id);
    alter table DBDistrict_DBLegislator 
        add constraint UK_neyks3k879m3g9wuqutimjdce  unique (legislators_id);
    alter table DBDistricts_DBDistrict 
        add constraint UK_2uiahvgobqsuby3loxlaaw7yb  unique (districtList_id);
    alter table DBDistricts_aggregateGroupMap 
        add constraint UK_8591nauyvlo84o69e4af265wh  unique (aggregateGroupMap_id);
    alter table DBDistricts_computationGroupMap 
        add constraint UK_j4hurmg0j2bu09y48uy4jmbik  unique (computationGroupMap_id);
    alter table DBGroup 
        add constraint UK_fnj4ivbsm7v9e9b4q29g7k9vk  unique (groupName);
    alter table DBGroupInfo_DBInfoItem 
        add constraint UK_gk2n11lfx77klw8ndyxy4mg8j  unique (groupItems_id);
    alter table assembly_aggregategroupmap 
        add constraint UK_9poknpmgasyyp1obdt5qoe5le  unique (aggregateGroupMap_id);
    alter table assembly_computationgroupmap 
        add constraint UK_ob68eulskxrjds0u3vhe0u84b  unique (computationGroupMap_id);
    alter table AggregateResults_resultList 
        add constraint FK_ffnfmudk8fof9dkp3sm5mmbhy 
        foreign key (AggregateResults_id) 
        references AggregateResults;
    alter table ComputationResults_resultList 
        add constraint FK_rxs52u3j9fwii6k76hptdo3oy 
        foreign key (ComputationResults_id) 
        references ComputationResults;
    alter table DBAssembly 
        add constraint FK_d831tqk4156qmn972x3awocod 
        foreign key (districts_id) 
        references DBDistricts;
    alter table DBAssembly_AggregateResults 
        add constraint FK_gfrlbw26jewxxfr9luue09xfr 
        foreign key (aggregateMap_id) 
        references AggregateResults;
    alter table DBAssembly_AggregateResults 
        add constraint FK_fj9dcei5o94ejf7eea5r39chs 
        foreign key (aggregateMap_KEY) 
        references DBGroup;
    alter table DBAssembly_AggregateResults 
        add constraint FK_dx6mg673nhtsad5w72mefxbgj 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    alter table DBAssembly_ComputationResults 
        add constraint FK_g8o5yrs20a7rkmgjjjihl181n 
        foreign key (computationMap_id) 
        references ComputationResults;
    alter table DBAssembly_ComputationResults 
        add constraint FK_2pn9fu0f9ploaw7ttjstb5yy0 
        foreign key (computationMap_KEY) 
        references DBGroup;
    alter table DBAssembly_ComputationResults 
        add constraint FK_e28i1c09ykq4bgnjwlm7j5jp0 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    alter table DBDistrict_AggregateResults 
        add constraint FK_lhbqs8saukjfjvm34v3dmhpst 
        foreign key (aggregateMap_id) 
        references AggregateResults;
    alter table DBDistrict_AggregateResults 
        add constraint FK_s09h9udp4ll5vu2y7e6d9vl8u 
        foreign key (aggregateMap_KEY) 
        references DBGroup;
    alter table DBDistrict_AggregateResults 
        add constraint FK_61j3bhtkqq5sm1exn8lvhcueh 
        foreign key (DBDistrict_id) 
        references DBDistrict;
    alter table DBDistrict_ComputationResults 
        add constraint FK_g8cavqboqvpss7f3i2uy4ivmi 
        foreign key (computationMap_id) 
        references ComputationResults;
    alter table DBDistrict_ComputationResults 
        add constraint FK_4h91nt33r0x0bv2nkf78c89sj 
        foreign key (computationMap_KEY) 
        references DBGroup;
    alter table DBDistrict_ComputationResults 
        add constraint FK_bq8n3dotxfuxnw2vw4qqd5swa 
        foreign key (DBDistrict_id) 
        references DBDistrict;
    alter table DBDistrict_DBLegislator 
        add constraint FK_neyks3k879m3g9wuqutimjdce 
        foreign key (legislators_id) 
        references DBLegislator;
    alter table DBDistrict_DBLegislator 
        add constraint FK_9xpiwbyuy1xjprewodxsf88cc 
        foreign key (DBDistrict_id) 
        references DBDistrict;
    alter table DBDistricts_DBDistrict 
        add constraint FK_2uiahvgobqsuby3loxlaaw7yb 
        foreign key (districtList_id) 
        references DBDistrict;
    alter table DBDistricts_DBDistrict 
        add constraint FK_r81r07dube1quemr7r7nrgs7o 
        foreign key (DBDistricts_id) 
        references DBDistricts;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_8591nauyvlo84o69e4af265wh 
        foreign key (aggregateGroupMap_id) 
        references DBGroupInfo;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_kwjleaqh3r5n2wpdv10ln3sbx 
        foreign key (aggregateGroupMap_KEY) 
        references DBGroup;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_2vht55myh4li65ppwblhk0rc2 
        foreign key (DBDistricts_id) 
        references DBDistricts;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_j4hurmg0j2bu09y48uy4jmbik 
        foreign key (computationGroupMap_id) 
        references DBGroupInfo;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_438t43u5g59cwbut6fxohjm1r 
        foreign key (computationGroupMap_KEY) 
        references DBGroup;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_epe6ngy0pw6dqbfbx0gj9f2nd 
        foreign key (DBDistricts_id) 
        references DBDistricts;
    alter table DBGroupInfo_DBInfoItem 
        add constraint FK_gk2n11lfx77klw8ndyxy4mg8j 
        foreign key (groupItems_id) 
        references DBInfoItem;
    alter table DBGroupInfo_DBInfoItem 
        add constraint FK_e9e3y0koemwc0yg16s1f1xpkw 
        foreign key (DBGroupInfo_id) 
        references DBGroupInfo;
    alter table assembly_aggregategroupmap 
        add constraint FK_9poknpmgasyyp1obdt5qoe5le 
        foreign key (aggregateGroupMap_id) 
        references DBGroupInfo;
    alter table assembly_aggregategroupmap 
        add constraint FK_qqslcx0jhkp9ueo9faajjhwlr 
        foreign key (aggregateGroupMap_KEY) 
        references DBGroup;
    alter table assembly_aggregategroupmap 
        add constraint FK_8p8bot46e5uka0bxr9cg5181s 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    alter table assembly_computationgroupmap 
        add constraint FK_ob68eulskxrjds0u3vhe0u84b 
        foreign key (computationGroupMap_id) 
        references DBGroupInfo;
    alter table assembly_computationgroupmap 
        add constraint FK_a7betyowd17b66w8rjiq36ypv 
        foreign key (computationGroupMap_KEY) 
        references DBGroup;
    alter table assembly_computationgroupmap 
        add constraint FK_phl554wenadqr28f1y8yhksp3 
        foreign key (DBAssembly_id) 
        references DBAssembly;
    create sequence hibernate_sequence;