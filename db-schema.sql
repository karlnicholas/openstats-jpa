
    alter table AggregateResults_resultList 
        drop constraint FK_ffnfmudk8fof9dkp3sm5mmbhy;
    alter table ComputationResults_resultList 
        drop constraint FK_rxs52u3j9fwii6k76hptdo3oy;
    alter table DBAssembly 
        drop constraint FK_d831tqk4156qmn972x3awocod;
    alter table DBAssembly_aggregateGroupMap 
        drop constraint FK_l8cxg6ne0j8elvmqlasgmx69l;
    alter table DBAssembly_aggregateGroupMap 
        drop constraint FK_gm393t4o489vqcvqgnoobmbih;
    alter table DBAssembly_aggregateGroupMap 
        drop constraint FK_kwrajflfi1op6x76xo5vsfcsn;
    alter table DBAssembly_aggregateMap 
        drop constraint FK_gn6drdud3xpm8lu9dnqnkk04u;
    alter table DBAssembly_aggregateMap 
        drop constraint FK_ebw42elbjaj0uiu1e2c7jnixu;
    alter table DBAssembly_aggregateMap 
        drop constraint FK_l5o1qwsgucjmb2475lkv9fmds;
    alter table DBAssembly_computationGroupMap 
        drop constraint FK_mlw0t9ffvrsvwp00n6x1at990;
    alter table DBAssembly_computationGroupMap 
        drop constraint FK_bfqcpu6m3afva7av8rthpwbss;
    alter table DBAssembly_computationGroupMap 
        drop constraint FK_pq8b0qv7nith0t5n0jmr96v36;
    alter table DBAssembly_computationMap 
        drop constraint FK_cyiyf4bf4jq8ojxxdjnnja59m;
    alter table DBAssembly_computationMap 
        drop constraint FK_bydykht2yf6wflyat366sk0mi;
    alter table DBAssembly_computationMap 
        drop constraint FK_38rnsufap3doc07y9aci69drj;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_neyks3k879m3g9wuqutimjdce;
    alter table DBDistrict_DBLegislator 
        drop constraint FK_9xpiwbyuy1xjprewodxsf88cc;
    alter table DBDistrict_aggregateMap 
        drop constraint FK_m1xrys1hx1eg44rn8qxldagvv;
    alter table DBDistrict_aggregateMap 
        drop constraint FK_498st27sl0wc28v9tybrk1fi6;
    alter table DBDistrict_aggregateMap 
        drop constraint FK_49o16p9jk7hmwbjhrf6t0n8un;
    alter table DBDistrict_computationMap 
        drop constraint FK_jt2x25kb5erd5x7xphjwjrek7;
    alter table DBDistrict_computationMap 
        drop constraint FK_rvws9iia8hyvukiufd1rtqcrb;
    alter table DBDistrict_computationMap 
        drop constraint FK_6aa9ip6bctmu3qamyorgda5n4;
    alter table DBDistricts_DBDistrict 
        drop constraint FK_2uiahvgobqsuby3loxlaaw7yb;
    alter table DBDistricts_DBDistrict 
        drop constraint FK_r81r07dube1quemr7r7nrgs7o;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_62ojm8lhuylj4pxbmawrgkc2p;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_9g32oml476e5bwwk7b8mbvgnc;
    alter table DBDistricts_aggregateGroupMap 
        drop constraint FK_d5k0q7d93cj4r1l8ua3rm5of9;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_cc76d8b851crib4ae3s3tqp0c;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_mixiaw6pcdh0cov9ud5d357mk;
    alter table DBDistricts_computationGroupMap 
        drop constraint FK_70735r0vpquhelm3s3gebfxr7;
    alter table DBGroupInfo_DBInfoItem 
        drop constraint FK_gk2n11lfx77klw8ndyxy4mg8j;
    alter table DBGroupInfo_DBInfoItem 
        drop constraint FK_e9e3y0koemwc0yg16s1f1xpkw;
    drop table if exists AggregateResults cascade;
    drop table if exists AggregateResults_resultList cascade;
    drop table if exists ComputationResults cascade;
    drop table if exists ComputationResults_resultList cascade;
    drop table if exists DBAssembly cascade;
    drop table if exists DBAssembly_aggregateGroupMap cascade;
    drop table if exists DBAssembly_aggregateMap cascade;
    drop table if exists DBAssembly_computationGroupMap cascade;
    drop table if exists DBAssembly_computationMap cascade;
    drop table if exists DBDistrict cascade;
    drop table if exists DBDistrict_DBLegislator cascade;
    drop table if exists DBDistrict_aggregateMap cascade;
    drop table if exists DBDistrict_computationMap cascade;
    drop table if exists DBDistricts cascade;
    drop table if exists DBDistricts_DBDistrict cascade;
    drop table if exists DBDistricts_aggregateGroupMap cascade;
    drop table if exists DBDistricts_computationGroupMap cascade;
    drop table if exists DBGroup cascade;
    drop table if exists DBGroupInfo cascade;
    drop table if exists DBGroupInfo_DBInfoItem cascade;
    drop table if exists DBInfoItem cascade;
    drop table if exists DBLegislator cascade;
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
    create table DBAssembly_aggregateGroupMap (
        DBAssembly int8 not null,
        DBGroupInfo int8 not null,
        DBGroup int8 not null,
        primary key (DBAssembly, DBGroup)
    );
    create table DBAssembly_aggregateMap (
        DBAssembly int8 not null,
        AggregateResults int8 not null,
        DBGroup int8 not null,
        primary key (DBAssembly, DBGroup)
    );
    create table DBAssembly_computationGroupMap (
        DBAssembly int8 not null,
        DBGroupInfo int8 not null,
        DBGroup int8 not null,
        primary key (DBAssembly, DBGroup)
    );
    create table DBAssembly_computationMap (
        DBAssembly int8 not null,
        ComputationResults int8 not null,
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
    create table DBDistrict_aggregateMap (
        DBDistrict int8 not null,
        AggregateResults int8 not null,
        DBGroup int8 not null,
        primary key (DBDistrict, DBGroup)
    );
    create table DBDistrict_computationMap (
        DBDistrict int8 not null,
        ComputationResults int8 not null,
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
    create table DBDistricts_aggregateGroupMap (
        DBDistricts int8 not null,
        DBGroupInfo int8 not null,
        DBGroup int8 not null,
        primary key (DBDistricts, DBGroup)
    );
    create table DBDistricts_computationGroupMap (
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
    alter table DBAssembly_aggregateGroupMap 
        add constraint UK_l8cxg6ne0j8elvmqlasgmx69l  unique (DBGroupInfo);
    alter table DBAssembly_aggregateMap 
        add constraint UK_gn6drdud3xpm8lu9dnqnkk04u  unique (AggregateResults);
    alter table DBAssembly_computationGroupMap 
        add constraint UK_mlw0t9ffvrsvwp00n6x1at990  unique (DBGroupInfo);
    alter table DBAssembly_computationMap 
        add constraint UK_cyiyf4bf4jq8ojxxdjnnja59m  unique (ComputationResults);
    alter table DBDistrict_DBLegislator 
        add constraint UK_neyks3k879m3g9wuqutimjdce  unique (legislators_id);
    alter table DBDistrict_aggregateMap 
        add constraint UK_m1xrys1hx1eg44rn8qxldagvv  unique (AggregateResults);
    alter table DBDistrict_computationMap 
        add constraint UK_jt2x25kb5erd5x7xphjwjrek7  unique (ComputationResults);
    alter table DBDistricts_DBDistrict 
        add constraint UK_2uiahvgobqsuby3loxlaaw7yb  unique (districtList_id);
    alter table DBDistricts_aggregateGroupMap 
        add constraint UK_62ojm8lhuylj4pxbmawrgkc2p  unique (DBGroupInfo);
    alter table DBDistricts_computationGroupMap 
        add constraint UK_cc76d8b851crib4ae3s3tqp0c  unique (DBGroupInfo);
    alter table DBGroup 
        add constraint UK_fnj4ivbsm7v9e9b4q29g7k9vk  unique (groupName);
    alter table DBGroupInfo_DBInfoItem 
        add constraint UK_gk2n11lfx77klw8ndyxy4mg8j  unique (groupItems_id);
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
    alter table DBAssembly_aggregateGroupMap 
        add constraint FK_l8cxg6ne0j8elvmqlasgmx69l 
        foreign key (DBGroupInfo) 
        references DBGroupInfo;
    alter table DBAssembly_aggregateGroupMap 
        add constraint FK_gm393t4o489vqcvqgnoobmbih 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBAssembly_aggregateGroupMap 
        add constraint FK_kwrajflfi1op6x76xo5vsfcsn 
        foreign key (DBAssembly) 
        references DBAssembly;
    alter table DBAssembly_aggregateMap 
        add constraint FK_gn6drdud3xpm8lu9dnqnkk04u 
        foreign key (AggregateResults) 
        references AggregateResults;
    alter table DBAssembly_aggregateMap 
        add constraint FK_ebw42elbjaj0uiu1e2c7jnixu 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBAssembly_aggregateMap 
        add constraint FK_l5o1qwsgucjmb2475lkv9fmds 
        foreign key (DBAssembly) 
        references DBAssembly;
    alter table DBAssembly_computationGroupMap 
        add constraint FK_mlw0t9ffvrsvwp00n6x1at990 
        foreign key (DBGroupInfo) 
        references DBGroupInfo;
    alter table DBAssembly_computationGroupMap 
        add constraint FK_bfqcpu6m3afva7av8rthpwbss 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBAssembly_computationGroupMap 
        add constraint FK_pq8b0qv7nith0t5n0jmr96v36 
        foreign key (DBAssembly) 
        references DBAssembly;
    alter table DBAssembly_computationMap 
        add constraint FK_cyiyf4bf4jq8ojxxdjnnja59m 
        foreign key (ComputationResults) 
        references ComputationResults;
    alter table DBAssembly_computationMap 
        add constraint FK_bydykht2yf6wflyat366sk0mi 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBAssembly_computationMap 
        add constraint FK_38rnsufap3doc07y9aci69drj 
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
    alter table DBDistrict_aggregateMap 
        add constraint FK_m1xrys1hx1eg44rn8qxldagvv 
        foreign key (AggregateResults) 
        references AggregateResults;
    alter table DBDistrict_aggregateMap 
        add constraint FK_498st27sl0wc28v9tybrk1fi6 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBDistrict_aggregateMap 
        add constraint FK_49o16p9jk7hmwbjhrf6t0n8un 
        foreign key (DBDistrict) 
        references DBDistrict;
    alter table DBDistrict_computationMap 
        add constraint FK_jt2x25kb5erd5x7xphjwjrek7 
        foreign key (ComputationResults) 
        references ComputationResults;
    alter table DBDistrict_computationMap 
        add constraint FK_rvws9iia8hyvukiufd1rtqcrb 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBDistrict_computationMap 
        add constraint FK_6aa9ip6bctmu3qamyorgda5n4 
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
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_62ojm8lhuylj4pxbmawrgkc2p 
        foreign key (DBGroupInfo) 
        references DBGroupInfo;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_9g32oml476e5bwwk7b8mbvgnc 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBDistricts_aggregateGroupMap 
        add constraint FK_d5k0q7d93cj4r1l8ua3rm5of9 
        foreign key (DBDistricts) 
        references DBDistricts;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_cc76d8b851crib4ae3s3tqp0c 
        foreign key (DBGroupInfo) 
        references DBGroupInfo;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_mixiaw6pcdh0cov9ud5d357mk 
        foreign key (DBGroup) 
        references DBGroup;
    alter table DBDistricts_computationGroupMap 
        add constraint FK_70735r0vpquhelm3s3gebfxr7 
        foreign key (DBDistricts) 
        references DBDistricts;
    alter table DBGroupInfo_DBInfoItem 
        add constraint FK_gk2n11lfx77klw8ndyxy4mg8j 
        foreign key (groupItems_id) 
        references DBInfoItem;
    alter table DBGroupInfo_DBInfoItem 
        add constraint FK_e9e3y0koemwc0yg16s1f1xpkw 
        foreign key (DBGroupInfo_id) 
        references DBGroupInfo;
    create sequence hibernate_sequence;