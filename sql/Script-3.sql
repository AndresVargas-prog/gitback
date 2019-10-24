/******USUARIOS DE SISEG******//*** Base de Datos dbcaa ***/

select * from proceso where moduloid=220;

select * from modulo where aplicacionid in (49,52,66);


select * from aplicacion;


/**USUARIOS Y PROCESOS DE EFECTINET**/

select * from consignatario;
select * from usuario;

select u.clienteid,u.consignatarioid,u.usuarioid,u.usuarionombre,u.usuariousr, u.usuariopwd,c.consignatarionombre,u.usuarioactivo,u.perfilid,u.usuarioid from usuario u 
join consignatario c using(clienteid,consignatarioid) where consignatarioactivo=true and u.usuariomodooperacion='P' and c.consignatariomodooperacion='P' 
and u.usuariousr='admin' AND u.clienteid=1 and u.consignatarioid=20;


select * from proceso where procesoclase like'%viajes%'

select * from perfil where perfilid=4

select * from permiso where perfilid=4 AND procesoid=193


INSERT INTO public.proceso
(procesoid, procesomodooperacion, procesotiposesion, procesoclase)
VALUES(219, 'P', 'PC', 'efectivale.despensaplus.estadocuenta.Reposiciones');


update perfil set perfilmenupro='<script language="JavaScript">
var MENU_ITEMS =
[
  ["Men&uacute; Efectinet",null,null,
      ["Login",null,null,
        ["Cambiar Contraseña","/efectinet/proceso?pid=3"],
        ["Cerrar Sesi&oacuten","/efectinet/proceso?pid=2"]
      ],
      ["Administraci&oacuten de Pedidos",null,null,
        ["Crear Pedido","/efectinet/proceso?pid=13"],
        ["Autorizar Pedido","/efectinet/proceso?pid=14"],
        ["Factura Remisi&oacuten","/efectinet/proceso?pid=196"],
        ["Consulta Complementos","/efectinet/proceso?pid=199"]
      ],
      ["Consultas Administrativas del Cliente ",null,null,
        ["Monedero Cliente","/efectinet/proceso?pid=15"],
        ["Pedidos ","/efectinet/proceso?pid=12"],
        ["Dep&oacutesitos","/efectinet/proceso?pid=16"]
      ],
      ["Administraci&oacuten de Servicios",null,null,
        ["Efecticard Combustible",null,null,
                     ["Administraci&oacuten Monedero Chip",null,null,
                                ["Dispersi&oacuten Tarjeta Chip","/efectinet/proceso?pid=32"],
                                ["Devoluci&oacuten Tarjeta Chip","/efectinet/proceso?pid=33"],
                                ["Lista de Dispersi&oacuten a Tarjeta Chip","/efectinet/proceso?pid=34"],
                                ["Lista Devoluci&oacuten","/efectinet/proceso?pid=64"]
                     ],
                     ["Administraci&oacuten Tarjeta Chip",null,null,
                                ["Par&aacutemetros chip","/efectinet/proceso?pid=35"],
                                ["Cambio de Nip en chip","/efectinet/proceso?pid=36"],
                                ["Correcci&oacuten de Od&oacutemetro chip","/efectinet/proceso?pid=37"],
                                ["Lista de Estaciones Chip","/efectinet/proceso?pid=38"],
                ["Consulta Consumo Tarjetas","/efectinet/proceso?pid=101"]
                     ],
                     ["Consultas Chip",null,null,
                                ["Monedero chip","/efectinet/proceso?pid=39"],
                                ["Pedido chip","/efectinet/proceso?pid=40"],
                                ["Tarjeta chip","/efectinet/proceso?pid=41"],
                                ["Consumos chip","/efectinet/proceso?pid=42"]
                     ]
        ],
        
        ["Efecticard Corporativo",null,null,
                     ["Administraci&oacuten Monedero Corporativo",null,null,
                                ["Dispersi&oacuten Tarjeta Corporativo","/efectinet/proceso?pid=22"],
                                ["Devoluci&oacuten Tarjeta Corporativo","/efectinet/proceso?pid=23"]
                     ],
                     ["Administraci&oacuten Tarjeta Corporativa",null,null,
                                ["Par&aacutemetros Tarjeta Corporativo","/efectinet/proceso?pid=24"],
                                ["Giros Permitidos Corporativo","/efectinet/proceso?pid=25"],
                                ["Establecimientos Corporativo","/efectinet/proceso?pid=26"],
                                ["Reposiciones Corporativo","/efectinet/proceso?pid=27"],
                                ["Renovacion de Tarjetas","/efectinet/proceso?pid=73"]
                     ],
                     ["Consultas Corporativo",null,null,
                                ["Monedero Corporativo","/efectinet/proceso?pid=28"],
                                ["Pedido Corporativo","/efectinet/proceso?pid=29"],
                                ["Tarjeta Corporativo","/efectinet/proceso?pid=30"],
                                ["Consumos Corportativo","/efectinet/proceso?pid=31"]
                     ]

        ],
        ["Efecticard Despensa",null,null,
                     ["Consulta Detalle Pedidos","/efectinet/proceso?pid=43"],
                     ["Consulta Tarjeta Despensa","/efectinet/proceso?pid=18"],
                     ["Reposiciones Despensa","/efectinet/proceso?pid=19"],
                     ["Actualiza Empleados","/efectinet/proceso?pid=81"],
                     ["Renovaci&oacute;n de Tarjetas","/efectinet/proceso?pid=141"],
                     ["Consulta Renovaciones","/efectinet/proceso?pid=140"]

        ],
        ["Efecticard Comida",null,null,
                    ["Administraci&oacuten Monedero Comida",null,null,
                                ["Saldos Vigencia Vencida","/efectinet/proceso?pid=119"],
                                ["Dispersi&oacuten Tarjeta Comida","/efectinet/proceso?pid=120"],
                                ["Pedidos Comida","/efectinet/proceso?pid=121"],
                                ["Monedero Comida","/efectinet/proceso?pid=122"]
                     ],
                     ["Administraci&oacuten Tarjeta Comida",null,null,
                                ["Consulta Tarjeta Comida","/efectinet/proceso?pid=20"],
                                ["Reposiciones Comida","/efectinet/proceso?pid=21"],
                                ["Renovacion de Tarjetas","/efectinet/proceso?pid=72"]
                     ]
        ],
        ["Efecticard Viajes y Gastos",null,null,                
                    ["Administraci&oacuten Monedero Viajes y Gastos",null,null,                 
                                //["Dispersi&oacuten Tarjeta Viajes","/efectinet/proceso?pid=192"],                   
                                //["Devoluci&oacuten Tarjeta Viajes","/efectinet/proceso?pid=193"],                   
                                //["Autorizaci&oacuten Saldo","/efectinet/proceso?pid=194"]                                  
                    ],              
                    ["Administraci&oacuten Tarjeta Viajes",null,null,                   
                                ["Reposiciones Tarjeta VyG","/efectinet/proceso?pid=191"],                  
                                //["Renovaci&oacute;n Tarjeta VyG","/efectinet/proceso?pid=25"],                  
                                ["Administraci&oacute;n Tarjetas VyG","/efectinet/proceso?pid=190"],
                                  ["Dispersi&oacute;n","/efectinet/proceso?pid=192"]                
                    ],              
                    ["Administraci&oacuten CC",null,null,                   
                                //["Opcion 1","/efectinet/proceso?pid=13"],                   
                                //["Opcion 2","/efectinet/proceso?pid=13"]                
                    ],              
                    ["Consultas Viajes y Gastos",null,null,                 
                                ["Monedero Viajes y Gastos","/efectinet/proceso?pid=186"],                  
                                ["Pedido Viajes y Gastos","/efectinet/proceso?pid=187"],                    
                                ["Tarjeta Viajes y Gastos","/efectinet/proceso?pid=188"], 

                                //Consumos Viajes y Gastos","/efectinet/proceso?pid=189"]               
                    ], 
                    ["Comprobantes Viajes y Gastos",null,null,                 
                                ["Comprobacion de Gastos","/efectinet/proceso?pid=202"],
                                ["Comprobacion de Gastos Tarjeta","/efectinet/proceso?pid=206"]

                                //Consumos Viajes y Gastos","/efectinet/proceso?pid=189"]               
                    ],
					["Actualiza Empleados", "/efectinet/proceso?pid=220"								
					]               
        ], 
        ["Efecticard Despensa Plus",null,null,
                      ["Consulta Detalle Pedidos","/efectinet/proceso?pid=213"],
                      ["Administraci&oacuten de Tarjetas",null,null,
                                  ["Activaci&oacuten e Inactivaci&oacuten","/efectinet/proceso?pid=211"],
                                  ["Cancelaci&oacuten Unitaria","/efectinet/proceso?pid=212"],
                                  ["Cancelaci&oacuten Masiva","/efectinet/proceso?pid=213"],
                                  ["Reposiciones","/efectinet/proceso?pid=215"]
                       ],
                       ["Consultas Despensa Plus",null,null,
                                  ["Consulta de Tarjetas y Saldos","/efectinet/proceso?pid=215"],
                                  ["Consumos","/efectinet/proceso?pid=216"]
                       ],
                                  ["Actualiza Empleados","/efectinet/proceso?pid=217"],
                                  ["Lista negra de comercios","/efectinet/proceso?pid=218"],
                                  ["Bloqueo por Zonas","/efectinet/proceso?pid=219"],
                                  ["Dispersi&oacuten Programada","/efectinet/proceso?pid=220"]
          ],
      ],
        
        ["Actualizaci&oacuten de Datos",null,null,
             ["Cuenta de correo","/efectinet/proceso?pid=68"]
      ]
      ]
];
new menu (MENU_ITEMS, MENU_TPL);
</script> ' where perfilid=4;
