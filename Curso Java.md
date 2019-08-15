                                                              ¿QUE ES JAVA?

	Java es un lenguaje de programación con el que podemos realizar cualquier tipo de programa. En la actualidad es un lenguaje
	muy extendido y cada vez cobra más importancia tanto en el ámbito de Internet como en la informática en general. 
	Está desarrollado por la compañía Sun Microsystems con gran dedicación y siempre enfocado a cubrir las necesidades tecnológicas
	más punteras.

	Una de las principales características por las que Java se ha hecho muy famoso es que es un lenguaje independiente de la 
	plataforma. Eso quiere decir que si hacemos un programa en Java podrá funcionar en cualquier ordenador del mercado. Es una 
	ventaja significativa para los desarrolladores de software, pues antes tenían que hacer un programa para cada sistema operativo, 
	por ejemplo Windows, Linux, Apple, etc. Esto lo consigue porque se ha creado una Máquina de Java para cada sistema que hace de 
	puente entre el sistema operativo y el programa de Java y posibilita que este último se entienda perfectamente.

	La independencia de plataforma es una de las razones por las que Java es interesante para Internet, ya que muchas personas deben 
	tener acceso con ordenadores distintos. Pero no se queda ahí, Java está desarrollándose incluso para distintos tipos de 
	dispositivos además del ordenador como móviles, agendas y en general para cualquier cosa que se le ocurra a la 

------------------------------------------------------------- tipos de datos ----------------------------------------------------------
	 
	datos pritivos( No aceptan Valor Null)
	tipo(Tamaño)
	byte(8 bits), short(16), int(32), long(64), float, double, char
	Datos No Primitivos ( Puedes almacenar Dato Null)
	Integer

	
------------------------------------------------------------- declarar constantes  
	Este tipo de variables, es una variable la cual no se modificara en el resto del programa
           
				Final int entero = 0; // Final palabra clave para declarar constante 

------------------------------------------------------------- Entrada de Datos()
	
	import java.until.Scanner
		main
			Scanner entrada  = new Scanner(System.in);
			int numero;
			string Cadena;

			System.out.println("Digite un numero")
	//Entrada
		Entero	numero = entrada.nextInt();
		Float	numero = entrada.nextFloat(); // Al ingresar tenemos que ponerlo 14,4 con coma ya que el punto te dara un error
		cadena	cadena = entrada.next(); // leera la proxima palabra hasta que encuentre un espacio
			cadena = entrada.next().charAt(0);// leera el primer caracter de la siguiente palabra que encuentre
			cadena = entrada.nextLine(); // leera la proxima linea de palabras esto hasta que encuentre un enter o salto de
							linea
	//Salida
			System.out.println("El Numero Es: "+ numero)

Entrada de datos() por jOptionsPane - Crea pequeñas ventanas para ingresar los datos requeridos

	import javax.Swing.JOptionPane;
		main
			string cadena
			int entero;
			char letra;
			double decimal;
	//Entrada
			cadena = JOptionPane.ShowInputDialog("Digite una cadena: ");
			entero = Integer.ParceInt(JOptionPane.ShowInputDialog("Digite un Entero: "));
			Float = Float.parceFloat(JOptionPane.ShowInputDialog("Digite un Decimal: "));
			letra = JOptionPane.ShowInputDialog("Digite un caracter").charAt(0); 
	//Salida
			JOptionPane.ShowMessageDialog(null. "La Cadena Es: "+ cadena);
			JOptionPane.ShowMessageDialog(null. "El Entero Es: "+ entero);
			JOptionPane.ShowMessageDialog(null. "El Decimal Es: "+ decimal);
			JOptionPane.ShowMessageDialog(null. "La letra Es: "+ letra);

------------------------------------------------------------- Combinacion de operadores

	int numero = 5;

	numero = numero + 5; //numero += 5; suma 
	numero = numero - 5; //numero -= 5; resta
	numero = numero / 5; //numero /= 5; div
	numero = numero * 5; //numero *= 5; multi
	numero = numero % 5; //numero %= 5; residuo

------------------------------------------------------------- sufijo prefijo

	int x= 5, y;

	Sufujo ->  y = x++; primero se le asigna el valor de x a y, y despues se incrementa x
	Prefijo -> y = --x; primero se incrementa x y despues de asignara x a y

------------------------------------------------------------- Condicionales

	Los condicionales son estructuras de codigo las cuales nos ayudan a la toma de decisiones, para esto en java existen los 
	Condicionales: 

		- if(condicion){} -Si la condicion se cumple se ejecuta el codigo de adentro
		- else if(){} si la condicion del primer if no se cumple, pasa a evaluar la del else if
		- else{} Si no se cumple el codigo del if se ejecuta el cogido de dentro

------------------------------------------------------------- Operador ternero

	sintaxis 
		valor = (condicion) ? valor1 : valor2

		si la condicion se cumple valor = valor1
		si la condicion no se cumple valor = valor2

------------------------------------------------------------- Clase Math

	Esta clase tiene muchas funciones cib las cuales podemos manejas los numeros de manera optima por ejemplo
	podemos sacar raiz cuandrada de un numero( Math.sqrt()), elevar un numero a una potencia(Math.pow(nume, Exp)), 
	generar un numero aleatorio(Math.Random() esta clase retorna un numero de entre 0 y 1 por lo que tenemos que 
	multiplicarlo por 10 para que nos retorne uno de 0 a 10) 

------------------------------------------------------------- Arreglos

   Un array en java es una estructura de datos que nos permite almacenar un conjunto de datos de un mismo tipo. 
   El tamaño de los arrays se declara en un primer momento y no se puede cambiar luego durante la ejecucion del programa

							      sintaxis
						tipo variable[] Nombre = new tipo variable[]

							Sintaxis for each
				 for(Tipo de datos del arreglo espacio nombre:nombre de mi arreglo)

								Ejemplo
				Arreglo : String[] nombres = {"Alejandro","Maria","Luisa","Juan","Luis"};
				metodo for each 
			for(String i:nombres){System.out.println("nombres:"+i);} esto imprimira todos los nombres que hay en el arreglo



------------------------------------------------------------- Busqueda Secuencial

	El método de búsqueda secuencial consiste en ir comparando el elemento o criterio de búsqueda con cada uno  de los elementos 
	en el arreglo, esto se hace recorriendo el arreglo y deteniéndose en cada elemento y hacer la comparación, en caso de ser 
	verdadera la comparación, guardar la posición el elemento o dato.

	He aquí el código:

			public  int busquedaSecuencial(int []arreglo,int dato){
				int posicion = -1;
				for(int i = 0; i < arreglo.length; i++){//recorremos todo el arreglo
				    if(arreglo[i] == dato){//comparamos el elemento en el arreglo con el buscado
					  posicion = i;//Si es verdadero guardamos la posicion
					  break;//Para el ciclo
					 }
				}
				return posicion;
			}
	
------------------------------------------------------------- Busqueda Binaria

	Esta busqueda se basa en dividir el arreglo en el que se buscara un determinado dato y atravez de comparaciones ir
	partiendo  una y otra vez el arreglo hasta que el numero se encuentre o se terminen las posiciones donde buscar

					public static int busquedaBinaria(int  vector[], int dato){
						int n = vector.length;
						int centro,inf=0,sup=n-1;
						 while(inf<=sup){
						   centro=(sup+inf)/2;
						   if(vector[centro]==dato) return centro;
						   else if(dato < vector [centro] ){
						      sup=centro-1;
						   }
						   else {inf=centro+1;}
						}
						return -1;
					}

-------------------------------------------------------------  Ordenamiento Burbuja --
    Es un sensillo algoritmo de ordenamiento. Funciona revisando cada elemento de la lista que va a ser ordenada con el siguiente, 
    intercambiandolos de posicion si estan en desorden. Es necesario revisar varias veces toda la lista hasta que no necesiten mas 
    intercambios, lo cual significa que la lista esta ordenada.

		codigo:

					for(int i = 0; i<elementos-1; i++)
						{   
						    for(int j = 0; j<elementos-1; j++){
							if(a[j]>a[j+1])
							{
							    temp = a[j];
							    a[j] = a[j+1];
							    a[j+1] = temp;
							}
						    }
					}

------------------------------------------------------------- Metodo por incersion  --

 	Este trabaja comparando el numero con el que esta en la izquierda si es mayor el que tiene a la izq cambia y se vuelve a 
	comparar hasta que no haya mas numeros en la izq.

	El método de ordenación por inserción directa consiste en recorrer todo el array comenzando desde el segundo elemento hasta 
	el final. Para cada elemento, se trata de colocarlo en el lugar correcto entre todos los elementos anteriores a él o sea entre
	los elementos a su izquierda en el array.

	Dada una posición actual p, el algoritmo se basa en que los elementos A[0], A[1], ..., A[p-1] ya están ordenados.
	
			Codigo :
			
						for(int i = 0; i<elementos; i++){
							pos = i;
							temp = a[i];

							while(pos>0 && a[pos-1] > temp)
							{
							    a[pos] = a[pos-1];
							    pos--;
							}
							a[pos] = temp;
						    }

------------------------------------------------------------- Matrices

    Una matriz en un arreglo bidimencional, necesita dos indices para acceder a sus elementos. Graficamente podemos representar una matriz como una tabla de n filas y m columnas cuyos elementos son todos del mismo tipo.

			Sintaxis de una matriz
		int a[][] = new int[x][y]; x,y = numero de filas y columnas

_______________________________________________________________________________________________________________________________________

-------------------------------------------------- Programacion Orientada A Objetos  --
        La programacion Orientada a objetos (POO) es una forma de programar, mas cercana a como expresariamos las cosas en la vida
	real que otros tipos de programacion.
        
	En programacion para contemplar un Objeto en programacion este tiene que tener 2 cosas: 
        
	        + Atributos(Caracteristicas) = color, marca, etc.
                + Metodos(Acciones) = encender, apagar, etc.

        Una clase es un conjunto de objetos con caracteristicas similares

-------------------------------------------------------- Metdos y Objetos

		- Un Metodo es :  una accion o comportamiento de los objetos 
		- Parametros es : una declaracion de variable u objeto
		- Argumentos es : un valor que se envia

-------------------------------------------------------- Metodo Constructor  --
    
	Cuando se crea un objeto ocurren  cosas:
        	+ Se asigna memoria para el objeto.
        	+ Se inicializan los atributos de ese objeto
        	+ Se invoca al constructor de la clase que puede ser uno entre varios
    	
	Caracteristicas:
        	+ Tiene el mismo nombre que la clase
        	+ No devuelve ningun valor
        	+ Debe declararse como publico

		Ejemplo
		
		public Persona(String nombre, int edad){// this se usa cuando ambos atributos y parametros son el mismo nombre
		        this.nombre = nombre;	// this diferencia el atributo de los parametros
			this.edad = edad;	// this.edad es el atributo y edad es el parametro que le pasamos
	        }
    
------------------------------- Sobrecarga de metodos Normales y Metodos constructores 
  
     La sobrecarga de metodos es una tecnica en la cual se puede declarar varias veces un metodo pero cada uno de estos tiene que
     estar diferenciado por alguna cosa, pueden llegar a llamarse igual pero sus parametros deben de ser diferentes
     
	Ejemplo:
    	
			public Persona(){System.out.println("Hola soy Abraham");}

			public Persona(int edad){
					int this.edad = edad;
					System.out.println("Hola soy Abraham y tengo" + edad+ " Años");}


   Ambos se llaman igual pero uno tiene parametros y otro no por lo cual a la hora de llamarlo se decidira cual es el que llamara:
   
				Persona p1 = new Persona(21);
                    			    o  
			        Persona p2 = new Persona();

	Java diferencia la sobrecarga de metodos mediante los parametros no por el tipo de retorno ya que esto crearia Ambigedad al 	
	llamar a uno de los metodos

	Ejemplo:
	// Declracion Erronea de los metodos
		public void suma(int n1, int n2){}
		public int suma(int n1, int n2){return suma;}
		
		al llamarlos no sabemos cual metodo es el que mandaria a llamar por esto es que ni java nos permite declarar 2 metodos 
		con parametros iguales, no importa si los metodos tiene diferente tipo de retorno:
		
	// Llamada de los metodos
		suma(n1,n2);
		suma(n1,n2);

------------------------------------------------------- Miembros Estaticos --

    	Estos datos ya no pertenesen a los objetos, es decir; los objetos ya no pueden modificar el dato individualmente, por lo que si 
	un objeto cual sea incluso uno nuevo cambia el valor de la variable estatica este valor cambiara para los otros objetos.

				    Ejemplo:

					private static String frase = "Hola";

					ob2.frase = "Segunda Frase";
        
        En este caso tanto los objetos ob1, ob2 y los que se creen nuevos ya no tendran como valor "Hola" sino que tendran "Segunda 
	frase", todos los objetos, si se llegara a cambiar de nuevo, la variable frase, tomara la ultima asignacion que se le haga,
	y repercutira en todos los objetos ya creados y nuevos.

        Ademas un dato estatico ya no es necesario instanciar un objeto, sino que ya podemos llamarla con la clase misma ya que este 
	no pertenece a los objetos si no que le pertenece a la clase.
	
			                        System.out.println(Main.frase);

        Inclusive al declarar metodos  estos pueden ser llamados desde la misma clase

                        		System.out.println("La Suma es: " + Main.suma(3,4));


---------------------------------------------------  Modificadores de Acceso  --

     Al ponerle el modificador public este puede ser usado en todas las clases de cualquier paquete, pero si no ponemos el modificador 
     se podra usar en todas las clases pero solo del paquete donde esta la clase que lo contiene.

       					                 public int edad;

     El modificador private restringira el uso en cualquier otra clase independientemente de si esta en el mismo proyecto, este dato
     solo se podra utilizar en la clase en la que se declaro en este caso solo en esta clase se puede usar.

				                        private int date;

     El modificador protected hace que las variables puedan ser accedidas por los miembros de la clase ademas de las clases hijas
     El modificador de acceso protected puede aplicarse a todos los miembros de una clase, es decir, tanto a campos como a métodos
     o constructores. En el caso de métodos o constructores protegidos, estos serán visibles/utilizables por las subclases y otras
     clases del mismo package. 
     
     El acceso protegido suele aplicarse a métodos o constructores, pero preferiblemente no a campos, para evitar debilitar el 
     encapsulamiento. En ocasiones puntuales sí resulta de interés declarar campos con acceso protegido.	

---------------------------------------------------  Encapsulamiento --

     El encapsulamiento se encarga de ocultar nustros atributos y metodos de las demas clases Esto nos ayuda a que no cualquier 
     persona pueda cambias los datos a su antojo si no que al estar encapsulados estos estan protegidos, y asi podemos prevenir 
     que puedan meter un dato erroneo y asi provocar un error fatal

----------------------------------------------- Metodos Accesores (Getters y Setters) --

     Los setters y getters son metodos que podemos crear para poder accesar a los datos encapsulados en la clase principal 
     
				        	- setters establese datos
       						- getters obtiene datos

--------------------------------------------------- Herencia     
    
    La herencia es una forma de reutilizacion de software en la que se crea una nueva clas al absorber los miembros de una ya existente.
     
						   Ejemplo

					Persona       (Clase Padre o SuperClase)

					//atributos
						- nombre : String
						- apellidos : String        - significa que es privado
						- edad : int
					//metodos
						+ Persona(nombre,apellido,edad)
						+ getNombre(): String
						+ getAoellido():String      + significa que es publica
						+ getEdad():int
	
	El hacer una clase heredada esta tendra todos los atributos y metodos de la superclase o clase padre o mas simple de la clase 
	que se deriva

					Estudiante  (Clase Hija o SubClase)
					//atributos
						- nombre : String
						- apellidos : String        - significa que es privado
						- edad : int
						- codigoEstudiante: int      
						- notaFinal: float
					//metodos
						+ Persona(nombre,apellido,edad)
						+ getNombre(): String
						+ getAoellido():String      + significa que es publica
						+ getEdad():int
						+ mostrarDatos() : void

	La clase hija o la que se creo apartir de la superclase tambien puede tener sus propios atributos y metodos, aparte de los 
	que se le fueron heredados como en el anterior ejemplo tiene sus propios atributos y metodos :

					- Atributos - codigoEstudiante, notaFinal
					- Metodos -   mostrarDatos

---------------------------------------------------  SobreEscritura  --
    
	El sobreescribir un metodo, significa que para cada clase hija el metodo puede ser diferente siempre y cuando le indiquemos 
	lo que modifiquamos 
        
				Ejemplo:

			class Animal(){ // Clase Padre o Superclase
			    public void comer(){
				System.out.println("Estoy comiendo"); // esto imprimira en caso de que no se 
			    }                                            sobreescriba el metodo en las clases 
			}                                                hijas siguientes

			class perro extends Animal{
			@Override   // esta etiqueta indica a java que se sobreescribira el metodo
			    public void comer(){
				System.out.println("Estoy comiendo en mi platito"); esto se imprimira 
			    }                                                       si se llama el metodo en un objeto de perro
			}								|
											|
					Perro perro = new Perro();  perro.comer(); = "Estoy comiendo en mi platito" 

			class vaca extends Animal{              Como aqui no hay metodo al llamar el metodo con
			}                                       un objeto de vaca imprimira el metodo de la superclase
									|  
					Vaca vaca = new Vaca(); vaca.comer(); = "Estoy comiendo"                                    


---------------------------------------------------  Metodos y Clases Abstractas
   
      --  Clases y Metodos Abstractos --

    	Clase Abstracta:    
        
	    + Se utilizan solo como SuperClases o Clase Padre
            + No se pueden Instancias objetos
            + Sirve para proporcionar una superclase apropiada a partir de  
              la cual heredan otras clases.

	Un metodo abstracto es un metodo que si o si se tiene que declarar pero que no sabemos bien como implementarlo

	Un método abstracto es un método declarado pero no implementado, es decir, es un método del que solo se escribe su nombre, 
	parámetros y tipo devuelto pero no su código. Los métodos abstractos se escriben sin llaves {} y con ; al final de la 
	declaración. Por ejemplo: public abstract double area();

	Supongamos un esquema de herencia que consta de la clase Profesor de la que heredan ProfesorInterino y ProfesorTitular. 
	Es posible que todo profesor haya de ser o bien ProfesorInterino o bien ProfesorTitular, es decir, que no vayan a existir 
	instancias de la clase Profesor. Entonces, ¿qué sentido tendría tener una clase Profesor? 

	El sentido está en que una superclase permite unificar campos y métodos de las subclases, evitando la repetición de código
 	y unificando procesos. Ahora bien, una clase de la que no se tiene intención de crear objetos, sino que únicamente sirve 
	para unificar datos u operaciones de subclases, puede declararse de forma especial en Java: como clase abstracta. 

	La declaración de que una clase es abstracta se hace con la sintaxis public abstract class NombreDeLaClase { … }.

---------------------------------------------------  Polimorfismo

	+ En una relacion de tipo heremcia, un objeto de la superclase puede almacenar un objeto de cualquiera de sus subclases

	+ Esto significa que la clase padre o superclase es compatible con los tipos que derivan de ella pero no al revez

 		       polimorfiso = poli      - "muchos" 
                		     morfismo  - "formas"
               
      por lo que el polimorfismo son las muchas formas que puede tomar un objeto

		Ejemplo: 
---------------------------------------------------  Diagrama UML para el ejemplo
			    UML
						Vehiculo    //Clase Padre
						
   					            # matricula: String
			     //Atributos     	    # marca: String        		# significa que son de tipo protected
					     	    # modelo: String          
			     //Metodo        	    + mostrarDatos: String
							      |
				|-----------------------------|----------------------------| Clases Hijas
				|                             |                            |
			   VehiculoTurismo            VehiculoDeportivo           VehiculoFurgoneta
			-numeroPuertas: int            -cilindrada: int            -carga: int
			+ mostrarDatos(): String       + mostrarDatos(): String    + mostrarDatos(): String
---------------------------------------------------------------------------------------------------------------------------------------

podemos crear un objeto de vehiculo     

        			Vehiculo miVehiculo = new Vehiculo(matricula,marca,modelo);

	En el concepto de polimorfismo nos dice que una clase padre puede almacenar cualquier objeto de sus clases hijas por lo que 
	podemos hacer esto:

        		Vehiculo miVehiculo2 = new VehiculoDeportivo(matricula,marca,modelo,cilindrada);

	en este caso creamos un objeto de vehiculo pero ya no lo instanciamos con la clase principal, sino que la podemos instanciar 
	por cualquiera de sus clases hijas (VehiculoDeportivo, VehiculoTurismo,VehiculoFurgoneta)

		Ejemplo: 

		Vehiculo misVehiculos[] = new Vehiculo[4]; arreglo de objetos
    
		misVehiculos[0] = new Vehiculo("GF4874","Ferrari","F12");
		misVehiculos[1] = new VehiculoTurismo("FG5774","Mini Van","GT457",4);  Un objeto de la clase padre esta siendo 
		misVehiculos[2] = new VehiculoDeportivo("GTX447","Audi","GTR-558",8);  instanciado por una de sus hijas
		misVehiculos[3] = new VehiculoFurgoneta("FR4457","Toyota","C450",50);

---------------------------------------------------  Tratamiento de Excepciones

	Que son las excepciones?
	Cuando un programa java viola las restricciones semanticas del lenguaje(se produce un error) la maquina virtual java comunica 
	este hecho al programa mediante una excepcion.

	Muchas clases de errores pueden provocar una excepcion, desde un desbordamiento de memoria o un disco duro dañado, un intento 
	de dividir entre 0 o intentarf acceder a un vector fuera de sus limites.cuando esto ocurre, la maquina virtual java genera un 
	objeto de la clase exeption.

	Excepciones Verificadas: Son errores de hardware las cuales no dependen del programador, pueden llegar a ser desde un error 
	con el disco duro, o con la falta de un archivo que fue borrado por una persona o programa externo, estas excepciones java 
	nos obliga a evadirla o solucionarla con el metodo try Catch o indicando las posibles excepciones al comenzar el main

Ejemplo: 

	se genera porque el archivo no existe o se ah borrado/*
    
				BufferedReader bf = new BufferedReader(new FileReader("d:\\prueba\\texto.txt"));
				String linea;
				while((linea= bf.readLine())!= null)
				{
					System.out.println(linea);
				}
			Exception in thread "main" java.io.FileNotFoundException

	Excepciones No Verificadas: al contrario de las verificadas estas si dependen de nosotros, son errores que cometemos los ´
	programadores a lo largo del desarrollo del programa, estos errores son, debido a posibles errores aritmeticos como dividir 
	entre 0, o tambien el guardar un dato de un tipo en otro de un tipo diferente como guardar un string en un entero.

Ejemplo:

				Dividir entre 0
					    int num1 = 5, num2 = 0;

					    int resultado = num1/num2;
					    System.out.println("El Resultado es: "+resultado);
			//Exception in thread "main" java.lang.ArithmeticException: / by zero
