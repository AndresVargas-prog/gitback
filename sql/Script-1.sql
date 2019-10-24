/** Base de Datos dbviajes **/
select * from tarjeta;
select * from bitacoracancelacion ;
select * from tbl_transaction_project limit 10;
select * from movimientomonedero limit 10;
select * from tarjetacuenta;
select * from consignatario;

select * from consignatario order by 1 limit 20

select c.clienteid,c.consignatarioid,t.tnuta,t.empleadonombre,t.empleadoid from tarjeta t join tarjetacuenta c using(clienteid,consignatarioid,cuentaid,tarjetaid,empleadoid)

--(clienteid, consignatarioid, tnuta ,empleadonombre, empleadoid) cuentaid = 11007
--( 2,  			21		5429170002931068,   PEREZ REYES ANGEL , 1200789)
insert into bitacoracancelacion values(10449, 21501384,1575,'2019-08-23 17:44:35', 2, 21, 17, 11007, 'T', '007', 'Efectinet', 'Prueba Cancelacion')
insert into tarjeta values(2, 21, 11007, 1200789, 'Andres Vargas', 17, 202109, 'P', false, false, 0, '2009-01-01 00:00:00', 0, 1494, -1, 'T', 5429170002931068, 0)

insert into consignatario values ( 2, 21, true, 'P', 'ND', 0.3);
insert into tarjetacuenta values ( 2, 21, 11007, 17, 1200789,'Andres Vargas', 20209, 'P', false, false, 0, 'T', 0, 0);
								--cliente cons cuenta targe empleid nombre, vigencia, mopera cance traspa saldo tipo param
select distinct cuentaid, motivocancelacion, comentarios from bitacoracancelacion where cuentaid = 91815;
update bitacoracancelacion set motivocancelacion = '007' where cuentaid = 11007;
				

						-- consulta que se utilizo para la creacion del modulo de Cancelacion de Tarjetas Definitivas --
select distinct t.clienteid,t.consignatarioid,t.tnuta,t.empleadonombre,t.cuentaid,t.empleadoid as nocuenta  from bitacoracancelacion c join tarjeta t using(clienteid,consignatarioid,tarjetaid,cuentaid)
where c.motivocancelacion='007'  order by clienteid,consignatarioid,tnuta;



				-- Droppeo de tablas (Borrado)
drop table public.batchCancelDefinitiva ; -- Borra la tabla batchCancelDefinitiva --
drop table public.batchCancelDefinitivaDetalle;
drop table public.bitacoraRCDetails;
drop table public.bitacoraRCD;

				-- Selects para checar la informacion de las tablas --
select * from batchCancelDefinitiva; -- selecciona todos los registros de la tabla batchCancelDefinitiva
select * from batchCancelDefinitivaDetalle;
select * from bitacorarcd;
select * from bitacorarcdetails;
SELECT * FROM bitacorarcd where rcdFileName = 'RB191010' and rcdProcessed = true

update bitacoracancelacion set motivocancelacion = '002' where cuentaid =  

				-- Inserciones a las tablas para probarlas --
INSERT INTO batchCancelDefinitivaDetalle (batch_cancelarid, detalle_Clienteid, detalle_consignatarioid, detalle_tarjeta, detalle_nombreEmpleado, detalle_empleadoid)
values(1, 1, 20, '1825364203443624', 'Andres Vargas', 123434)
INSERT INTO batchCancelDefinitiva (batch_identificadorfile, batch_pathSalida) VALUES ('21332213', 'ggsta/ggafs/hahafs/')

-- STORED PROCEDURE PARA CANCELAR LAS TARJETAS DE ESTA BASE DE DATOS T YA NO NOS APARESCAN EN LA CONSULTA
select * from sp_cancelaciondefinitiva('5429170002931007', 1, 20, 'ARROYO BECERRIL DIEGO');

update batchCancelDefinitivaDetalle set detalle_liberado = true where detalle_tarjeta = '5429170002931007' and detalle_nombreEmpleado = 'ARROYO BECERRIL DIEGO';

select * from bitacorarcdetails;
update bitacoraRCDetails set rcdetail_processed = true where rcdetail_card = '5429170002931007' and rcdetail_Empleado = 'ARROYO BECERRIL DIEGO';
																			  --5429170054393007
select distinct * from bitacoracancelacion where motivocancelacion = '10';

update batchCancelDefinitivaDetalle set detalle_liberado = true where detalle_tarjeta = '5429170002931007';

update bitacoracancelacion set motivocancelacion = '007', comentarios = 'Cancelacion Masiva' where motivocancelacion = '10';

select * from bitacoracancelacion;
select * from tarjeta where empleadoid = '1300281';
update tarjeta set empleadoid = '1300281' where empleadoid ='1300281A';


select * from bitacoracancelacion where motivocancelacion = '007';

				-- Creacion de tablas Manualmente  --
create table public.batchCancelDefinitiva (
	batch_cancelarid serial not null,
	batch_fechaRegistro timestamp null default now(),
	batch_identificadorfile varchar (30) not null,
	batch_pathSalida varchar(75),
	batch_respuesta boolean null default false,
	batch_fechaRespuesta timestamp,
	batch_pathEntrada varchar(75),
	constraint batch_cancelarid_pkey primary key (batch_cancelarid)
);

create table public.batchCancelDefinitivaDetalle(
	detalle_cancelDefinitivaid serial not null,
	batch_cancelarid int4 not null,
	detalle_fechaRegistro timestamp null default now(),
	detalle_Clienteid int4 not null,
	detalle_consignatarioid int4 not null,
	detalle_tarjeta varchar(20) not null,
	detalle_nombreEmpleado varchar(75) not null,
	detalle_empleadoid int4 not null,
	detalle_liberado boolean null default false,
	constraint detalle_cancelDefinitivaid_pkey primary key (detalle_cancelDefinitivaid)
);
insert into bitacorarcd (rcdFileName, rcd_Path, rcdnoregistros)values('RB191009', 'C:\Users\edgar.vargas\Desktop\FilesInverlatViajes\entrada\RB191009.txt',
17)

create table bitacoraRCD(
	rcdid serial not null,
	rcdRegister_Date timestamp null default now(),
	rcdFileName varchar(300) not null,
	rcd_Path varchar (300) not null,
	rcdRead_Date timestamp null default now(),
	rcdProcessed boolean null default false,
	rcdCliente varchar not null,
	rcdConsignatario varchar not null,
	rcdNoRegistros int4 not null,
	rcdSend_Date date null default now(),
	rcdComments varchar(300),
	
	constraint rcdid_pkey primary key (rcdid)
	);

create table bitacoraRCDetails(
	rcdetailid serial not null,
	rcdid int4 not null,
	rcdetailRegister_Date timestamp null default now(),
	rcdetailFile_Name varchar(300) not null,
	rcdetail_Path varchar (300) not null,
	rcdetail_Empleado varchar(200) not null,
	rcdetail_NoEmpleado varchar(50) not null,
	rcdetail_Card varchar (16) not null,
	rcdetail_NoCuenta varchar (20) not null,
	rcdetail_TipoTarjeta varchar(7) not null,
	rcdetail_saldo varchar(30) not null,
	rcdetail_TipoCancelacion varchar(50) not null,
	rcdetail_cancelar boolean not null,
	rcdetail_processed boolean null default false,
	
	constraint rcdetailid_pkey primary key (rcdetailid),
	constraint rcdid_fkey foreign key (rcdid) references bitacoraRCD(rcdid)
);

-- permissions

ALTER TABLE public.bitacoracancelacion OWNER TO postgres;
GRANT ALL ON TABLE public.bitacoracancelacion TO postgres;

-- stored procedure
CREATE OR REPLACE FUNCTION public.sp_cancelaciondef(tar character varying, clien int4, consig int4, nomb character varying)
	RETURNS boolean
	LANGUAGE plpgsql
AS $function$ 	
	declare 
		const record;
	begin
		if exists(select distinct t.clienteid,t.consignatarioid,t.tnuta,t.empleadonombre,t.empleadoid as nocuenta  
		from bitacoracancelacion c join tarjeta t using(clienteid,consignatarioid,tarjetaid,cuentaid)
					where c.motivocancelacion='007'  order by clienteid,consignatarioid,tnuta)
		then 
			
			select distinct t.clienteid,t.consignatarioid,t.tnuta,t.empleadonombre,t.cuentaid,t.empleadoid as nocuenta  
			into const from bitacoracancelacion c join tarjeta t using(clienteid,consignatarioid,tarjetaid,cuentaid)
			where c.motivocancelacion='007' and t.tnuta = tar and t.clienteid = clien and t.consignatarioid = consig
			and t.empleadonombre = nomb;
		
			update bitacoracancelacion set motivocancelacion = '10', comentarios = 'Cancelacion Definitiva' 
			where motivocancelacion = '007' and cuentaid = const.tnuta;
			if not found then
				return false;
			end if;
		
			return true;
		else
			return false;
		end if;
	end;
$function$






/** Proyecto Cambio de Correo electronico**/
declare arr text array[5][5] = '';
declare ints text Array[][] = '{{1,2,3},{4,5,6}}';
select * from tablausrsws(arr);

select * from tbl_users;
select * from tbl_users where id_user = '4280';
select * from tarjeta where tnuta = '5429174000007400';
select * from tarjetacuenta;
select tu.id_user, t.tnuta, t.empleadonombre from tarjeta as t join tbl_users as tu on ( t.empleadoid = tu.employee_number);

select t.cuentaid, t.empleadoid as  employee_number, t.empleadonombre as name , t.tnuta into usrcuen from tarjeta t join tarjetacuenta using (cuentaid);
select * from usrcuen;
drop table usrcuen;
drop function tablausrsws;

select * from tarjeta;
select * from tarjetacuenta;
select * from movimientotarjeta;
select * from consignatario;
select * from tbl_users where id_user = '4280';
select * from tarjetacuenta where empleadoid = '1300154';
select * from tarjeta as t join tarjetacuenta tc using(empleadoid) where empleadoid = '1300154';

insert into tarjeta values(1,20,00007,1300154, 'EDGAR ANDRES VARGAS MORA', 00007400, 202108, 'P', false, false, 1234,'2009-01-01 00:00:00',0,1534,-1,'T', 5429174000007400, 0);

insert into tbl_users values(4280, 2, 1300154, 'andreus@gmail.com', 'EDGAR ANDRES VARGAS MORA',' ', NULL, ' ', 5448466241, 'EV2@vyg.efectivale.com.mx', ' ', ' ', NULL, NULL, true, '2018-09-18 21:58:47', ' ', ' ');


-- consulta para crear un usuario que no existe pero tiene una tarjeta vinculada
select max(id_user) as maximo from tbl_users as tu where id_user = maximo;
select * from tbl_users as tu where id_user = 1189;
	int id_us = id_user + 1;
	int emplo_number += +1; 
insert into tbl_users values(id_user, id_type, employee_number, email,name,last_name,birthday, password, phone, email_product, job_title, access_token,access_token_expiry_date, last_session_date, active, creation_date, office_phone, office_extension);
insert into tbl_users values(id_us, 1,emplo_number , 'correo p', 'Luis Pineda', ' ', null,' ','7788451235', 'q@vyg.efectivale', 'admin',' ',null,now,true, now, ' ', ' ');

---------

								cli con cuen    tarid    empid         nom                   vigen   moop  cance  trsp   sald  tipo paramid temis
insert into tarjetacuenta values (1,20,00007, 00007400, 1300154, 'EDGAR ANDRES VARGAS MORA', 202108, 'P', false, false, 1234, 'T', 1534, 0);

-- insertamos una nueva tarjeta de prueba
tarjeta   5429174000007400 --tabla tarjeta
iduser 4280 -- tabla tbl_users

-- ejemplo tarjeta 
tarjeta   5429170042156011
tarjetaid         42156011 
cuenta            42156

select tbw.tuws2, tbw.tuws1,tbw.tuws2, tbu.employee_number, tbw.name, tbu.phone, tbu.email, tbu.email_product 
from tablausrsws('{{5429170019261000,5429170019261,LOPEZ MORENO MOISES OSCAR,19261000,804.00,''},{5429170056060000,5429170056060,MARIO GONZALEZ MIRELES    ,56060000,0.40,''},{5429170097765005,5429170097765,JONATHAN EZEQUIEL MEDINA C,97765005,1032.49,''}}') as tbw join tbl_users tbu on (tbw.name) = tbu."name";

select distinct tc.tnuta, tc.cuentaid, tu.employee_number as empleadoid ,tu.name,tu.phone, tu.email, tu.email_product from tbl_users tu join usrcuen tc using (name, employee_number);

select  tu.employee_number as empleadoid ,tu.name,tu.phone, tu.email, tu.email_product, tc.cuentaid, tc.tnuta from tbl_users tu join usrcuen tc using (name, employee_number);

select * from parametros;
select *from tarjeta where tnuta = '5429170074813000';

select * from tbl_users where tbl_users.email = lower('PHANTOM_173@HOTMAIL.COM');

select * from tbl_users as tu ;where id_user = 1200773

select * from tbl_company as tc2;
select * from tbl_cards as tc;

-- Desasignar Una Tarjeta
SELECT efectivale_backoffice_sp_reasign_card_vyg(
'5429171007408009', -- No de Tarjeta
'marla.martinez@lacastellana.com',  -- Correo con la que esta asignada
'marla.martinez@winelife.com.mx',  -- Correo Nuevo al que se quiere asignar
'MM76@vyg.efectivale.com.mx', -- Correo de la empresa a la que pertenece
'LEMON', -- Nombre del Cliente
'LIFE SADECV', -- Apellido ?
'1', -- numero de empleado
'Gerente Admin', -- posicion 
'5556989890', -- Numero de Telefono
'CDMX',  
'14080' -- Zip Code
)

--Asignar Una tarjeta
SELECT efectivale_backoffice_sp_create_cardholder(
                '000001', -- cliente 
                'abajoel0@gmail.com', -- email del cliente
                'LM2@vyg.efectivale.com.mx', -- email empresa
                'LUIS MARTINEZ LOPEZ', -- Nombre Cliente
                '', -- apellido 
                '1034', -- numero de Empleado
                'Desarrollador', -- Puesto
                '5581888888', -- Telefono
                'cdmxx',
                '', -- Zip Code
                '5429170064963005' -- Numero de Tarjeta
);
-- usuarios que tienen mas de una tarjeta asociada
JMARTINEZ@ARMIT-SOLUTIONS.COM                     
ABAJOEL07@GMAIL.COM                               

select * from tbl_cards join tbl_users using (id_user) where tbl_users.email = 
lower('R1_3@EFECTIVALE.COM.MX');
select * from tbl_users as tu where email = lower('DH4@vyg.efectivale.com.mx');

SELECT tbl_users."name",tbl_users.id_user,tbl_users.employee_number,tbl_users.phone,tbl_cards.id_card, 
tbl_users.email, tbl_users.email_product,convert_from(decrypt(dearmor(
tbl_cards.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),'aes'::
text),'SQL_ASCII') AS card_number, tbl_cards.id_type AS card_type--, tbl_cards.active
FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = tbl_users.id_user WHERE 
tbl_users.email = lower('phantom_173@hotmail.com');;

select * from tbl_cards as tc;
select * from tbl_users as ta;

select * from tbl_users ;
insert into tbl_users values(1178,1,1300306,lower('DIANAPALACIOS1994@HOTMAIL.COM'),'PALACIOS JUAREZ DIANA','',null, null, '5581497580','1@vyg.efectivale.com.mx','',null,null,null,true,'2018-09-06 09:06:13', null, null);

select * from tbl_cards as tc order by id_user desc;

select max(id_user) from tbl_users;
-- Desasignar Una Tarjeta
SELECT efectivale_backoffice_sp_reasign_card_vyg(
'5429170029694000', -- No de Tarjeta
'luis_calderon_94@hotmail.com',  -- Correo con la que esta asignada la tarjeta que se quiere mover
'joaquin54@gmail.com',  -- Correo Nuevo al que se quiere asignar que no exista en tbl_users
'jv0@vyg.efectivale.com.mx', -- Correo de la empresa a la que pertenece el usuario nuevo no la tarjeta
'PALACIOS JUAREZ DIANA', -- Nombre del usuario nuevo
'', -- Apellido ?
'1300306', -- numero de empleado de usuario nuevo
'Desarrollador', -- posicion de usuario nuevo
'5581497580', -- Numero de Telefono de usuario nuevo 
'CDMXx',  
'' -- Zip Code
);

select * from tbl_users as tu where email = 'luis_calderon_94@hotmail.com';

select * from sp_tarjetasv('','5429170094393009','');

select * from pruebaexist('{ {5429170074813000, } }');

select * from pruebaexist('
{probarcorreos.txt}') where existe = false;
select * from tarjeta where tnuta = '5429170002931007';
select * from tarjeta as t join tbl_users as tbu on (t.tnuta = '5429170012016005' and tbu.employee_number = t.empleadoid and t.empleadoid = '1300295')


SELECT convert_from(decrypt(dearmor(tc.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),'aes'::text), 'SQL_ASCII') AS card_number
				FROM tbl_cards as tc INNER JOIN tbl_users as tu ON tc.id_user = tu.id_user and tc.id_type = 1

				SELECT COUNT(*) FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = tbl_users.id_user WHERE tbl_users.email = lower(?) and tbl_cards.id_type = 1;
			
SELECT COUNT(*) FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = tbl_users.id_user where tbl_users.email = lower('diego@armit-solutions.com') and tbl_cards.id_type = 1;
				
SELECT tu.name, tu.id_user, tu.employee_number, tu.phone, tc.id_card, tu.email, tu.email_product, convert_from(decrypt(dearmor(tc.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),'aes'::text), 'SQL_ASCII') AS card_number, tc.id_type AS card_type FROM tbl_cards as tc INNER JOIN tbl_users as tu ON tc.id_user = tu.id_user and tc.id_type = 1

------------------------------ Funcion para BUSCAR TARJETAS ---------------------------------------- 
CREATE OR REPLACE FUNCTION public.sp_tarjetasv()
 RETURNS TABLE(idcard character varying,Tarjeta character varying,id_card character varying,card_type character varying,Name character varying,	id_user character varying,	NoEmpleado character varying, phone character varying,emailP character varying,	emailEm character varying)
 LANGUAGE plpgsql
AS $function$
	
	declare var_ins record;
	BEGIN
		
		if(noemp != '') then
				for var_ins in (SELECT tbl_users.name,tbl_users.id_user,tbl_users.employee_number,tbl_users.phone,tbl_cards.id_card,tbl_users.email, 
			tbl_users.email_product,convert_from(decrypt(dearmor(tbl_cards.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),
			'aes'::text),'SQL_ASCII') AS card_number, tbl_cards.id_type AS card_type FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = 
			tbl_users.id_user and tbl_cards.id_type = 1 and tbl_users.employee_number = NoEmp order by tbl_users.name)
			
			loop 
				idcard		:=  var_ins.id_card;
				Tarjeta 	:=	var_ins.card_number; 
				id_card 	:=	var_ins.id_card; 
				card_type	:=	var_ins.card_type; 
				Name 		:=  var_ins.name; 
				id_user 	:=	var_ins.id_user; 
				NoEmpleado 	:=	var_ins.employee_number; 
				phone 		:=	var_ins.phone; 
				emailP 		:=	var_ins.email; 
				emailEm 	:=	var_ins.email_product; 
				return next;
			end loop;
		end if;
	
		if (tar != '')	then
			for var_ins in (SELECT tbl_users.name,tbl_users.id_user,tbl_users.employee_number,tbl_users.phone,tbl_cards.id_card,tbl_users.email, 
			tbl_users.email_product,convert_from(decrypt(dearmor(tbl_cards.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),
			'aes'::text),'SQL_ASCII') AS card_number, tbl_cards.id_type AS card_type FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = 
			tbl_users.id_user and tbl_cards.id_type = 1 and convert_from(decrypt(dearmor(tbl_cards.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),
			'aes'::text),'SQL_ASCII') = tar order by tbl_users.name)
			loop 
				idcard		:=  var_ins.id_card;
				Tarjeta 	:=	var_ins.card_number; 
				id_card 	:=	var_ins.id_card; 
				card_type	:=	var_ins.card_type; 
				Name 		:=  var_ins.name; 
				id_user 	:=	var_ins.id_user; 
				NoEmpleado 	:=	var_ins.employee_number; 
				phone 		:=	var_ins.phone; 
				emailP 		:=	var_ins.email; 
				emailEm 	:=	var_ins.email_product; 
				return next;
			end loop;
		end if;
	
		if(cor != '') then
			for var_ins in (SELECT tbl_users.name,tbl_users.id_user,tbl_users.employee_number,tbl_users.phone,tbl_cards.id_card,tbl_users.email, 
			tbl_users.email_product,convert_from(decrypt(dearmor(tbl_cards.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),
			'aes'::text),'SQL_ASCII') AS card_number, tbl_cards.id_type AS card_type FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = 
			tbl_users.id_user and tbl_cards.id_type = 1 and tbl_users.email = cor order by tbl_users.name)
			loop 
				idcard		:=  var_ins.id_card;
				Tarjeta 	:=	var_ins.card_number; 
				id_card 	:=	var_ins.id_card; 
				card_type	:=	var_ins.card_type; 
				Name 		:=  var_ins.name; 
				id_user 	:=	var_ins.id_user; 
				NoEmpleado 	:=	var_ins.employee_number; 
				phone 		:=	var_ins.phone; 
				emailP 		:=	var_ins.email; 
				emailEm 	:=	var_ins.email_product; 
				return next;
			end loop;
		end if;
	
		if(noemp = '' and tar = '' and cor = '') then
			for var_ins in (SELECT tbl_users.name,tbl_users.id_user,tbl_users.employee_number,tbl_users.phone,tbl_cards.id_card,tbl_users.email, 
			tbl_users.email_product,convert_from(decrypt(dearmor(tbl_cards.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),
			'aes'::text),'SQL_ASCII') AS card_number, tbl_cards.id_type AS card_type FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = 
			tbl_users.id_user and tbl_cards.id_type = 1 order by tbl_users.name)
		
		loop 
				idcard		:=  var_ins.id_card;
				Tarjeta 	:=	var_ins.card_number; 
				id_card 	:=	var_ins.id_card; 
				card_type	:=	var_ins.card_type; 
				Name 		:=  var_ins.name; 
				id_user 	:=	var_ins.id_user; 
				NoEmpleado 	:=	var_ins.employee_number; 
				phone 		:=	var_ins.phone; 
				emailP 		:=	var_ins.email; 
				emailEm 	:=	var_ins.email_product; 
				return next;
			end loop;	
		end if;
	END;
$function$
;
-----------------------------------------------------------------


-- 	pruebas 

	select * from tbl_users where email = 'beat47@live.com';
	SELECT tu.name, tu.id_user, tu.employee_number, tu.phone, tc.id_card, tu.email, tu.email_product, convert_from(decrypt(dearmor(tc.card_number),decode(
	'98A7B031EAA2A491F515942A27A41783','escape'::text),'aes'::text), 'SQL_ASCII') AS card_number, tc.id_type AS card_type FROM tbl_cards as tc INNER JOIN tbl_users as tu 
	ON tc.id_user = tu.id_user and tc.id_type = 1 and tu.email = 'beat47@live.com';




SELECT tbl_users.name,tbl_users.id_user,tbl_users.employee_number,tbl_users.phone,tbl_cards.id_card,tbl_users.email, 
		tbl_users.email_product,convert_from(decrypt(dearmor(tbl_cards.card_number),decode('98A7B031EAA2A491F515942A27A41783','escape'::text),
		'aes'::text),'SQL_ASCII') AS card_number, tbl_cards.id_type AS card_type FROM tbl_cards INNER JOIN tbl_users ON tbl_cards.id_user = 
		tbl_users.id_user and tbl_cards.id_type = 1 order by tbl_users.name
		
		select tnuta,empleadoid,empleadonombre from tarjeta 
