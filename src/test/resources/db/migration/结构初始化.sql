
declare

  tb_name VARCHAR2(50);
  cursor table_cursor is
    select t.table_name
      from all_tables t
     where upper(t.table_name) in ('CAUGE_METRICS','COUNTER_METRICS','HISTOGRAM_METRICS','METER_METRICS','TIMER_METRICS')
       and upper(t.owner) = upper((select username from user_users));

begin

  open table_cursor;
  LOOP
    exit when table_cursor%notfound;
    tb_name := null;
    FETCH table_cursor into tb_name;
    if tb_name is null then
      dbms_output.put_line('Metric tables have been cleared. ');
      exit;
    end if;
    dbms_output.put_line('drop table ' || tb_name);
    Execute Immediate ('drop table ' || tb_name);
  
  end LOOP;
  close table_cursor;

end;
/
 
commit;

-- Create table
create table CAUGE_METRICS (
  	TIMESTAMP  		VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  	NAME    		VARCHAR2(200),
  	VALUE    		VARCHAR2(10)
);
-- Add comments to the table 
comment on table CAUGE_METRICS  is '度量指标信息表';
-- Add comments to the columns 
comment on column CAUGE_METRICS.TIMESTAMP  is '度量发送时间';
comment on column CAUGE_METRICS.NAME  is '度量指标名称';
comment on column CAUGE_METRICS.VALUE  is '度量指标值';

-- Create table
create table COUNTER_METRICS (
  	TIMESTAMP  		VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  	NAME    		VARCHAR2(200),
  	COUNT    		VARCHAR2(10)
);
-- Add comments to the table 
comment on table COUNTER_METRICS  is '计数数据信息表';
-- Add comments to the columns 
comment on column COUNTER_METRICS.TIMESTAMP  is '计数时间';
comment on column COUNTER_METRICS.NAME  is '计数指标名称';
comment on column COUNTER_METRICS.COUNT  is '计数结果值';

-- Create table
create table HISTOGRAM_METRICS (
  	TIMESTAMP  		VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  	NAME    		VARCHAR2(200),
  	COUNT    		VARCHAR2(10),
  	MAX				VARCHAR2(10),
  	MEAN    		VARCHAR2(10),
  	MIN    			VARCHAR2(10),
  	STDDEV    		VARCHAR2(10),
  	P50    			VARCHAR2(10),
  	P75    			VARCHAR2(10),
  	P95    			VARCHAR2(10),
  	P98    			VARCHAR2(10),
  	P99    			VARCHAR2(10),
  	P999    		VARCHAR2(10)
);
-- Add comments to the table 
comment on table HISTOGRAM_METRICS  is '直方图数据信息表';
-- Add comments to the columns 
comment on column HISTOGRAM_METRICS.TIMESTAMP  is '统计时间';
comment on column HISTOGRAM_METRICS.NAME  is '度量指标名称';
comment on column HISTOGRAM_METRICS.COUNT  is '访问的总次数';
comment on column HISTOGRAM_METRICS.MAX  is '最长时间';
comment on column HISTOGRAM_METRICS.MEAN  is '平均时间';
comment on column HISTOGRAM_METRICS.MIN  is '最短时间';
comment on column HISTOGRAM_METRICS.STDDEV  is '标准差';
comment on column HISTOGRAM_METRICS.P50  is '中位数';
comment on column HISTOGRAM_METRICS.P75  is '75th 分位数';
comment on column HISTOGRAM_METRICS.P95  is '95th 分位数';
comment on column HISTOGRAM_METRICS.P98  is '98th 分位数';
comment on column HISTOGRAM_METRICS.P99  is '99th 分位数';
comment on column HISTOGRAM_METRICS.P999  is '999th 分位数';

-- Create table
create table METER_METRICS (
  	TIMESTAMP  		VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  	NAME    		VARCHAR2(200),
  	COUNT    		VARCHAR2(10),
  	MEAN_RATE    	VARCHAR2(10),
  	M1_RATE    		VARCHAR2(10),
  	M5_RATE    		VARCHAR2(10),
  	M15_RATE    	VARCHAR2(10),
  	RATE_UNIT    	VARCHAR2(10)
);
-- Add comments to the table 
comment on table METER_METRICS  is 'TPS数据信息表';
-- Add comments to the columns 
comment on column METER_METRICS.TIMESTAMP  is '度量时间';
comment on column METER_METRICS.NAME  is '度量指标名称';
comment on column METER_METRICS.COUNT  is '访问的总次数';
comment on column METER_METRICS.MEAN_RATE  is '平均每秒请求数';
comment on column METER_METRICS.M1_RATE  is '1分钟 请求数/每秒的比率';
comment on column METER_METRICS.M5_RATE  is '5分钟 请求数/每秒的比率';
comment on column METER_METRICS.M15_RATE  is '15分钟 请求数/每秒的比率';
comment on column METER_METRICS.RATE_UNIT  is 'calls/second，比率单位';


-- Create table
create table TIMER_METRICS (
  	TIMESTAMP  		VARCHAR2(30) default to_char(sysdate,'yyyy-mm-dd hh24:mi:ss'),
  	NAME    		VARCHAR2(200),
  	COUNT    		VARCHAR2(10),
  	MAX				VARCHAR2(10),
  	MEAN    		VARCHAR2(10),
  	MIN    			VARCHAR2(10),
  	STDDEV    		VARCHAR2(10),
  	P50    			VARCHAR2(10),
  	P75    			VARCHAR2(10),
  	P95    			VARCHAR2(10),
  	P98    			VARCHAR2(10),
  	P99    			VARCHAR2(10),
  	P999    		VARCHAR2(10),
  	MEAN_RATE    	VARCHAR2(10),
  	M1_RATE    		VARCHAR2(10),
  	M5_RATE    		VARCHAR2(10),
  	M15_RATE    	VARCHAR2(10),
  	RATE_UNIT    	VARCHAR2(10),
  	DURATION_UNIT   VARCHAR2(10)
);
-- Add comments to the table 
comment on table TIMER_METRICS  is 'TPS、直方图数据信息表';
-- Add comments to the columns 
comment on column TIMER_METRICS.TIMESTAMP  is '度量时间';
comment on column TIMER_METRICS.NAME  is '度量指标名称';
comment on column TIMER_METRICS.COUNT  is '访问的总次数';
comment on column TIMER_METRICS.MAX  is '最长时间';
comment on column TIMER_METRICS.MEAN  is '平均时间';
comment on column TIMER_METRICS.MIN  is '最短时间';
comment on column TIMER_METRICS.STDDEV  is '标准差';
comment on column TIMER_METRICS.P50  is '中位数';
comment on column TIMER_METRICS.P75  is '75th 分位数';
comment on column TIMER_METRICS.P95  is '95th 分位数';
comment on column TIMER_METRICS.P98  is '98th 分位数';
comment on column TIMER_METRICS.P99  is '99th 分位数';
comment on column TIMER_METRICS.P999  is '999th 分位数';
comment on column TIMER_METRICS.MEAN_RATE  is '平均每秒请求数';
comment on column TIMER_METRICS.M1_RATE  is '1分钟 请求数/每秒的比率';
comment on column TIMER_METRICS.M5_RATE  is '5分钟 请求数/每秒的比率';
comment on column TIMER_METRICS.M15_RATE  is '15分钟 请求数/每秒的比率';
comment on column TIMER_METRICS.RATE_UNIT  is 'calls/second，比率单位';
comment on column TIMER_METRICS.DURATION_UNIT  is '该Timer的单位';

