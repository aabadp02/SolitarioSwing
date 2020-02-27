package solitarioswing;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Random;
import java.util.stream.IntStream;
import static javax.swing.JFrame.EXIT_ON_CLOSE;


class SolitarioSwing extends JFrame // Creacion de la ventana que aparecerá al compilar
{
    JLayeredPane panel = new JLayeredPane(); //Utilizamos JLayeredPane porque vamos a necesitar que los componentes puedan estar en diferentes dimensiones y que se puedan colocar unos sobre otros
    Carta[] baraja = new Carta[52];          //Aquí guardaremos todas las cartas que componen la baraja
    Carta[] control = new Carta[52];
    int contador = 28;    
    int limite = 52;                         //Este contador nos servirá para recorrer el array de numeros aleatorios entre 1 y 40 que será importante para generar las cartas. Lo ponemos a 28 porque ya habremos puesto28 cartas sobre la mesa
    MueveCapa mc = new MueveCapa();          //Método que serivirá para poner arriba del todo el componente que hayamos clicado.
    AccionBoton ab = new AccionBoton();
    int[] numerosAleatorios = IntStream.rangeClosed(0, 51).toArray(); //En este array guardamos los  numeros del 1 al 40 y luego será desordenado
    int repeticion = 0;                      //Numero de veces que volveremos a empezar a poner las cartas sobre la pila original
    JMenu archivo = new JMenu("Archivo");
    JMenu editar = new JMenu("Editar");
    JMenu historial = new JMenu("Historial");
    JMenu ayuda = new JMenu("Ayuda");
    
    Font obj = new Font("Bahnschrift", Font.BOLD, 14);
    
    JMenuItem nuevo = new JMenuItem("Nuevo");
    JMenuItem cargar = new JMenuItem("Cargar");
    JMenuItem guardar = new JMenuItem("Guardar");
    JMenuItem guardarComo = new JMenuItem("Guardar como");
    JMenuItem salir = new JMenuItem("Salir");
    
    JMenuItem deshacer = new JMenuItem("Deshacer");
    JMenuItem hacer = new JMenuItem("Hacer");
    JMenuItem resolver = new JMenuItem("Resolver");
    
    JMenuItem estadisticas = new JMenuItem("Estadisticas");
    JMenuItem fichero = new JMenuItem("Fichero");
    JMenuBar jmb = new JMenuBar();
    
    JButton reponer = new JButton("Reponer");
    
    String ficheroDestino = "SaveSolitario.txt";
    
    int decidirFichero = 2;
    
    //Declaracion de todas las pilas donde podrá haber cartas
    
    Pila todasLasCartas = new Pila( -1); //La pila donde pondremos las cartas al salir de la baraja
    Pila primeraPila = new Pila(-1);
    Pila segundaPila = new Pila(-1);
    Pila terceraPila = new Pila(-1);
    Pila cuartaPila = new Pila(-1);
    Pila quintaPila = new Pila(-1);
    Pila sextaPila = new Pila(-1);
    Pila septimaPila = new Pila(-1);
    
    //Declaración de las pilas finales con las cartas ordenadas por palos
    
    Pila pilaDeOros = new Pila(0);            //Con los números le indicaremos qué palos pueden ir en las pilas.
    Pila pilaDeEspadas = new Pila(1);
    Pila pilaDeBastos = new Pila(2);
    Pila pilaDeCopas = new Pila(3);
    
    int contadorPica = 0;
    int contadorTrevol = 0;
    int contadorCorazon = 0;
    int contadorDiamante = 0;
    
    GeneradorDeCartas cart = new GeneradorDeCartas();
    
    public SolitarioSwing() //Constructor de la clase 
    {
        
        setBounds(100, 100, 1500, 1080);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SOLITARIO");
        setResizable(false);
        
        int contadorBaraja = 0;              //Contador para recorrer el array de la baraja e ir introduciendo en él cada carta.
        
        //Rellenamos el mazo
        for(int palo = 0; palo < 4; palo++)
        {
            for(int numero = 0; numero < 13; numero++)
            {
                baraja[contadorBaraja] = new Carta(palo, numero, 1);
                contadorBaraja++;
            }
        }
        control = baraja;
        //desordenando los elementos
        Random r = new Random();
        for (int i = numerosAleatorios.length; i > 0; i--)
        {
            int posicion = r.nextInt(i);
            int tmp = numerosAleatorios[i-1];
            numerosAleatorios[i - 1] = numerosAleatorios[posicion];
            numerosAleatorios[posicion] = tmp;
        }
        
        //Ponemos el JLabel que servirá de baraja
        
                        //Este será el dorso que al clicar generará las cartas.
        
        panel.setPreferredSize(new Dimension(300, 300));
        
        getContentPane().add(panel); //Añadimos el JLayeredPane
        
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Image logo = miPantalla.getImage(getClass().getResource("/Imagenes/PosibleLogo.jpg"));
        setIconImage(logo);
        
        ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/Imagenes/TapeteCartas.jpg"));       //Ponemos la imagen de fondo
        JLabel imagenDeFondo = new JLabel(imagenFondo);
        
        imagenDeFondo.setOpaque(true);
        imagenDeFondo.setBounds(0, 0, 1920, 1080);
        panel.add(imagenDeFondo, new Integer(0));
        panel.setPosition(imagenDeFondo, 0);
        
       // panel.setBackground(new Color(51, 153, 45));
        panel.setOpaque(true);
              
        cart.setOpaque(true);                                           //Añadimos el generador de las cartas.
        cart.setBounds(30, 30, 100, 139);
        panel.add(cart, new Integer(0));
        panel.setPosition(cart, 0);    
        
        ImageIcon palo1 = new ImageIcon(getClass().getResource("/Imagenes/puramierda.jpg"));
        JLabel imagenPalo1 = new JLabel(palo1);
        
        imagenPalo1.setOpaque(true);
        imagenPalo1.setBounds(1057, 30, 100, 139);
        panel.add(imagenPalo1, new Integer(0));
        panel.setPosition(imagenPalo1, 0);
        
        ImageIcon palo2 = new ImageIcon(getClass().getResource("/Imagenes/puramierda2.jpg"));
        JLabel imagenPalo2 = new JLabel(palo2);
        
        imagenPalo2.setOpaque(true);
        imagenPalo2.setBounds(1057, 229, 100, 139);
        panel.add(imagenPalo2, new Integer(0));
        panel.setPosition(imagenPalo2, 0);
        
        ImageIcon palo3 = new ImageIcon(getClass().getResource("/Imagenes/puramierda3.jpg"));
        JLabel imagenPalo3 = new JLabel(palo3);
        
        imagenPalo3.setOpaque(true);
        imagenPalo3.setBounds(1057, 428, 100, 139);
        panel.add(imagenPalo3, new Integer(0));
        panel.setPosition(imagenPalo3, 0);
        
        ImageIcon palo4 = new ImageIcon(getClass().getResource("/Imagenes/puramierdaBien.jpg"));
        JLabel imagenPalo4 = new JLabel(palo4);
        
        imagenPalo4.setOpaque(true);
        imagenPalo4.setBounds(1057, 627, 100, 139);
        panel.add(imagenPalo4, new Integer(0));
        panel.setPosition(imagenPalo4, 0);
        
        ImageIcon fondoBaraja = new ImageIcon(getClass().getResource("/Imagenes/puramierdaB.jpg"));
        JLabel imagenBaraja = new JLabel(fondoBaraja);
        
        reponer.setOpaque(true);
        reponer.setBounds(30, 400, 100, 60);
        reponer.setBackground(Color.black);
        reponer.setForeground(Color.white);
        reponer.setFont(obj);
        reponer.setFocusPainted(false);
        panel.add(reponer, new Integer(0));
        panel.setPosition(reponer, 0);
        reponer.setVisible(false);
        
        reponer.addActionListener(ab);
        
        imagenBaraja.setOpaque(true);
        imagenBaraja.setBounds(30, 200, 100, 139);
        panel.add(imagenBaraja, new Integer(0));
        panel.setPosition(imagenBaraja, 0);
        
        jmb.setOpaque(true);
        jmb.setBounds(0, 0, 1920, 20);
        panel.add(jmb, new Integer(0));
        panel.setPosition(jmb, 0);
        
        jmb.add(archivo);
        jmb.add(editar);
        jmb.add(historial);
        jmb.add(ayuda);
        
        archivo.add(nuevo);
        archivo.add(cargar);
        archivo.add(guardar);
        archivo.add(guardarComo);
        archivo.add(salir);
        
        editar.add(deshacer);
        editar.add(hacer);
        editar.add(resolver);
        
        historial.add(estadisticas);
        historial.add(fichero);
        
        nuevo.addActionListener(ab);
        cargar.addActionListener(ab);
        guardar.addActionListener(ab);
        guardarComo.addActionListener(ab);
        salir.addActionListener(ab);
        
        deshacer.addActionListener(ab);
        ayuda.addActionListener(ab);
        
        //Ponemos las cartas correspondientes sobre la mesa.
        
        //Pila 7
        for(int i = 0; i < 7; i++)
        {
            if(i != 6)
            {
                baraja[numerosAleatorios[i]].darLaVuelta();
            }
            baraja[numerosAleatorios[i]].setOpaque(true);
            baraja[numerosAleatorios[i]].setBounds(927, 30 * (i + 1), 100, 139);
            panel.add(baraja[numerosAleatorios[i]], new Integer(0));
            panel.setPosition(baraja[numerosAleatorios[i]], 1); //Se generará una carta aleatoria en la posicion indicada
            baraja[numerosAleatorios[i]].setOrigin(927, 30 * (i + 1)); //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
            panel.moveToFront(baraja[numerosAleatorios[i]]); //Indicaremos que ese será el componente que esté arriba
                
            septimaPila.cartaSiPrincipal(baraja[numerosAleatorios[i]]);
            baraja[numerosAleatorios[i]].addMouseListener(mc);
        }
        for(int i = 7; i < 13; i++)
        {
            if(i != 12)
            {
                baraja[numerosAleatorios[i]].darLaVuelta();
            }
            baraja[numerosAleatorios[i]].setOpaque(true);
            baraja[numerosAleatorios[i]].setBounds(797, 30 * (i + 1  - 7), 100, 139);
            panel.add(baraja[numerosAleatorios[i]], new Integer(0));
            panel.setPosition(baraja[numerosAleatorios[i]], 1); //Se generará una carta aleatoria en la posicion indicada
            baraja[numerosAleatorios[i]].setOrigin(797, 30 * (i + 1 - 7)); //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
            panel.moveToFront(baraja[numerosAleatorios[i]]); //Indicaremos que ese será el componente que esté arriba
                
            sextaPila.cartaSiPrincipal(baraja[numerosAleatorios[i]]);
            baraja[numerosAleatorios[i]].addMouseListener(mc);
        }
        
        for(int i = 13; i < 18; i++)
        {
            if(i != 17)
            {
                baraja[numerosAleatorios[i]].darLaVuelta();
            }
            baraja[numerosAleatorios[i]].setOpaque(true);
            baraja[numerosAleatorios[i]].setBounds(667, 30 * (i + 1  - 13), 100, 139);
            panel.add(baraja[numerosAleatorios[i]], new Integer(0));
            panel.setPosition(baraja[numerosAleatorios[i]], 1); //Se generará una carta aleatoria en la posicion indicada
            baraja[numerosAleatorios[i]].setOrigin(667, 30 * (i + 1 - 13)); //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
            panel.moveToFront(baraja[numerosAleatorios[i]]); //Indicaremos que ese será el componente que esté arriba
                
            quintaPila.cartaSiPrincipal(baraja[numerosAleatorios[i]]);
            baraja[numerosAleatorios[i]].addMouseListener(mc);
        }
        
        for(int i = 18; i < 22; i++)
        {
            if(i != 21)
            {
                baraja[numerosAleatorios[i]].darLaVuelta();
            }
            baraja[numerosAleatorios[i]].setOpaque(true);
            baraja[numerosAleatorios[i]].setBounds(537, 30 * (i + 1  - 18), 100, 139);
            panel.add(baraja[numerosAleatorios[i]], new Integer(0));
            panel.setPosition(baraja[numerosAleatorios[i]], 1); //Se generará una carta aleatoria en la posicion indicada
            baraja[numerosAleatorios[i]].setOrigin(537, 30 * (i + 1 - 18)); //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
            panel.moveToFront(baraja[numerosAleatorios[i]]); //Indicaremos que ese será el componente que esté arriba
                
            cuartaPila.cartaSiPrincipal(baraja[numerosAleatorios[i]]);
            baraja[numerosAleatorios[i]].addMouseListener(mc);
        }
        
        for(int i = 22; i < 25; i++)
        {
            if(i != 24)
            {
                baraja[numerosAleatorios[i]].darLaVuelta();
            }
            baraja[numerosAleatorios[i]].setOpaque(true);
            baraja[numerosAleatorios[i]].setBounds(407, 30 * (i + 1  - 22), 100, 139);
            panel.add(baraja[numerosAleatorios[i]], new Integer(0));
            panel.setPosition(baraja[numerosAleatorios[i]], 1); //Se generará una carta aleatoria en la posicion indicada
            baraja[numerosAleatorios[i]].setOrigin(407, 30 * (i + 1 - 22)); //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
            panel.moveToFront(baraja[numerosAleatorios[i]]); //Indicaremos que ese será el componente que esté arriba
                
            terceraPila.cartaSiPrincipal(baraja[numerosAleatorios[i]]);
            baraja[numerosAleatorios[i]].addMouseListener(mc);
        }
        
        for(int i = 25; i < 27; i++)
        {
            if(i != 26)
            {
                baraja[numerosAleatorios[i]].darLaVuelta();
            }
            baraja[numerosAleatorios[i]].setOpaque(true);
            baraja[numerosAleatorios[i]].setBounds(277, 30 * (i + 1  - 25), 100, 139);
            panel.add(baraja[numerosAleatorios[i]], new Integer(0));
            panel.setPosition(baraja[numerosAleatorios[i]], 1); //Se generará una carta aleatoria en la posicion indicada
            baraja[numerosAleatorios[i]].setOrigin(277, 30 * (i + 1 - 25)); //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
            panel.moveToFront(baraja[numerosAleatorios[i]]); //Indicaremos que ese será el componente que esté arriba
                
            segundaPila.cartaSiPrincipal(baraja[numerosAleatorios[i]]);
            baraja[numerosAleatorios[i]].addMouseListener(mc);
        }
        
        for(int i = 27; i < 28; i++)
        {
            baraja[numerosAleatorios[i]].setOpaque(true);
            baraja[numerosAleatorios[i]].setBounds(157, 30 * (i + 1  - 27), 100, 139);
            panel.add(baraja[numerosAleatorios[i]], new Integer(0));
            panel.setPosition(baraja[numerosAleatorios[i]], 1); //Se generará una carta aleatoria en la posicion indicada
            baraja[numerosAleatorios[i]].setOrigin(157, 30 * (i + 1 - 27)); //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
            panel.moveToFront(baraja[numerosAleatorios[i]]); //Indicaremos que ese será el componente que esté arriba
                
            primeraPila.cartaSiPrincipal(baraja[numerosAleatorios[i]]);
            baraja[numerosAleatorios[i]].addMouseListener(mc);
        }
        
        setVisible(true);             
    }
    
    public class Dialog extends JDialog
    {
        public Dialog()
        {
            setTitle("Ayuda");
            setResizable(false);
            setBounds(500, 500, 200, 200);
            
            JTextPane texto = new JTextPane();
            texto.setText("Prueba");
            texto.setBackground(getContentPane().getBackground());
            texto.setEditable(false);
            
            add(texto);
            
        }
    }
    
    public void saveData(String fichero)
    {
        try
        {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fichero));
            
            //Guardamos lo que haya en el generador de Cartas (pila todasLasCartas) todo lo relacionado
            bw.write("" + contador); //Guardamos la posicion del contador
            bw.newLine();
            bw.write("" + limite); //Guardamos el valor del limite
            bw.newLine();
            bw.write("" + repeticion);
            bw.newLine();
            
           /* if(repeticion == 0)
            {
                for(int i = contador; i < limite; i++)
                {
                    int posicion = EncuentraCartaBaraja(baraja[i]);
                    bw.write("" + posicion);
                    bw.newLine();
                }
            }*/
            bw.write("" + todasLasCartas.numeroCartas());
            bw.newLine();
            
            //Le daremos cada carta y su posicion en pila
            for(int i = 0; i < todasLasCartas.numeroCartas(); i++)
            {
                //Llamamos al metodo encuentra carta.
                bw.write("" + EncuentraCarta(i, todasLasCartas) ); //Le pasamos la i para que compare cada carta de la pila y un 0 representando la pila "todasLasCartas"
                bw.newLine();                        // Hemos guardado la posicion que ocupa la carta en baraja[] (para saber qué carta es)
            }
            
            //Guardamos lo que hay en la primera pila
            bw.write("" + primeraPila.numeroCartas()); //Guardamos el numero de cartas en la pila
            bw.newLine();
            
            for(int i = 0; i < primeraPila.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, primeraPila);
                bw.write("" + posicion); //Posicion de la carta en control
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en la segunda pila
            bw.write("" + segundaPila.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < segundaPila.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, segundaPila);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en la tercera pila
            bw.write("" + terceraPila.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < terceraPila.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, terceraPila);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en la cuarta pila
            
            bw.write("" + cuartaPila.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < cuartaPila.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, cuartaPila);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en la quinta pila
            
            bw.write("" + quintaPila.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < quintaPila.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, quintaPila);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en la sexta pila
            
            bw.write("" + sextaPila.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < sextaPila.numeroCartas(); i++)
            {
                int posicion =  EncuentraCarta(i, sextaPila);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en la septima pila
            
            bw.write("" + septimaPila.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < septimaPila.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, septimaPila);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en el palo de las picas
            
            bw.write("" + pilaDeOros.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < pilaDeOros.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, pilaDeOros);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en el palo de los trevoles
            
            bw.write("" + pilaDeEspadas.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < pilaDeEspadas.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, pilaDeEspadas);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hay en el palo de los Corazones
            
            bw.write("" + pilaDeBastos.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < pilaDeBastos.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, pilaDeBastos);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
            
            //Guardamos lo que hayn el el palo de los diamantes
            bw.write("" + pilaDeCopas.numeroCartas());
            bw.newLine();
            
            for(int i = 0; i < pilaDeCopas.numeroCartas(); i++)
            {
                int posicion = EncuentraCarta(i, pilaDeCopas);
                bw.write("" + posicion);
                bw.newLine();
                bw.write("" + control[posicion].getReverso());
                bw.newLine();
            }
         
            bw.close();      
            
            
        }
        catch(Exception e)
        {
            System.out.println("Ha habido un error al guardar partida");
        }
    }
    
    public void loadData(String fichero)
    {
       /* for(int i = 0;i < baraja.length; i++)
        {
            baraja[i] = null;
        }*/
        todasLasCartas.vaciarPila();
        primeraPila.vaciarPila();
        segundaPila.vaciarPila();
        terceraPila.vaciarPila();
        cuartaPila.vaciarPila();
        quintaPila.vaciarPila();
        sextaPila.vaciarPila();
        septimaPila.vaciarPila();
        pilaDeOros.vaciarPila();
        pilaDeEspadas.vaciarPila();
        pilaDeBastos.vaciarPila();
        pilaDeCopas.vaciarPila();
        int contadorTodasLasCartas = 0;

        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fichero));
            
            contador = Integer.parseInt(br.readLine());
            limite = Integer.parseInt(br.readLine());
            repeticion = Integer.parseInt(br.readLine());

            contadorTodasLasCartas = Integer.parseInt(br.readLine()); //Guardamos el numero de cartas que tendremos en la pila TODASLASCARTAS
            //Cargamos las cartas de la pila todasLasCartas
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                posicionCarta = Integer.parseInt(br.readLine());

                    control[posicionCarta].descubrir();
                    todasLasCartas.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(30, 200);
                    panel.moveToFront(control[posicionCarta]);
                    control[posicionCarta].setOrigin(30, 200);
            }
            
            //Cargamos las cartas de la primera pila
            contadorTodasLasCartas = 0;
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    primeraPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(157, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(157, 30 * (control[posicionCarta].getPosicion() + 1));
                }
                else
                {
                    control[posicionCarta].descubrir();
                    primeraPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(157, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(157, 30 * (control[posicionCarta].getPosicion() + 1));
                    
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    segundaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(277, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(277, 30 * (control[posicionCarta].getPosicion() + 1));
                }
                else
                {
                    control[posicionCarta].descubrir();
                    segundaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(277, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(277, 30 * (control[posicionCarta].getPosicion() + 1));
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    terceraPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(407, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(407, 30 * (control[posicionCarta].getPosicion() + 1));
                }
                else
                {
                    control[posicionCarta].descubrir();
                    terceraPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(407, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(407, 30 * (control[posicionCarta].getPosicion() + 1));
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    cuartaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(537, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(537, 30 * (control[posicionCarta].getPosicion() + 1));
                }
                else
                {
                    control[posicionCarta].descubrir();
                    cuartaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(537, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(537, 30 * (control[posicionCarta].getPosicion() + 1));
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    quintaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(667, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(667, 30 * (control[posicionCarta].getPosicion() + 1));
                }
                else
                {
                    control[posicionCarta].descubrir();
                    quintaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(667, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(667, 30 * (control[posicionCarta].getPosicion() + 1));
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    sextaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(797, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(797, 30 * (control[posicionCarta].getPosicion() + 1));
                }
                else
                {
                    control[posicionCarta].descubrir();
                    sextaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(797, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(797, 30 * (control[posicionCarta].getPosicion() + 1));
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    septimaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(927, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(927, 30 * (control[posicionCarta].getPosicion() + 1));
                }
                else
                {
                    control[posicionCarta].descubrir();
                    septimaPila.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(927, 30 * (control[posicionCarta].getPosicion() + 1));
                    control[posicionCarta].setOrigin(927, 30 * (control[posicionCarta].getPosicion() + 1));
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    pilaDeOros.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(927, 30);
                    control[posicionCarta].setOrigin(927, 30 );
                }
                else
                {
                    control[posicionCarta].descubrir();
                    pilaDeOros.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(927, 30);
                    control[posicionCarta].setOrigin(927, 30);
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    pilaDeEspadas.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(1057, 229);
                    control[posicionCarta].setOrigin(1057, 229);
                }
                else
                {
                    control[posicionCarta].descubrir();
                    pilaDeEspadas.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(1057, 229);
                    control[posicionCarta].setOrigin(1057, 229);
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    pilaDeBastos.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(1057, 428);
                    control[posicionCarta].setOrigin(1057, 428);
                }
                else
                {
                    control[posicionCarta].descubrir();
                    pilaDeBastos.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(1057, 428);
                    control[posicionCarta].setOrigin(1057, 428);
                }
            }
            
            contadorTodasLasCartas = Integer.parseInt(br.readLine());
            
            for(int i = 0; i < contadorTodasLasCartas; i++)
            {
                int posicionCarta = 0;
                int reverso = 0;
                posicionCarta = Integer.parseInt(br.readLine());
                reverso = Integer.parseInt(br.readLine());
                
                if(reverso == 0)
                {
                    control[posicionCarta].darLaVuelta();
                    pilaDeCopas.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(1057, 627);
                    control[posicionCarta].setOrigin(1057, 627);
                }
                else
                {
                    control[posicionCarta].descubrir();
                    pilaDeCopas.cartaSiPrincipal(control[posicionCarta]);
                    control[posicionCarta].setLocation(1057, 627);
                    control[posicionCarta].setOrigin(1057, 627);
                }
            }
            br.close();
        }
        catch(Exception e)
        {
            System.out.println("Se ha producido un error al cargar la partida");           
        }
    }
    
    class AccionBoton implements ActionListener
    {

        @Override
        public void actionPerformed(ActionEvent e)
        {
            if(e.getSource() == reponer)
            {
                reponer.setVisible(false);
                for(int i = 0; i < limite; i++)
                {
                    baraja[i].setVisible(false);
                }
                cart.setVisible(true);
            }
            else if(e.getSource() == guardar)
            {
                saveData(ficheroDestino);
            }
            else if(e.getSource() == cargar)
            {
                loadData(ficheroDestino);
            }
            else if(e.getSource() == nuevo)
            {
                new SolitarioSwing();
            }
            else if(e.getSource() == guardarComo)
            {
                JButton open = new JButton();
                JFileChooser fc = new JFileChooser();
                fc.setCurrentDirectory(new java.io.File("."));
                fc.setDialogTitle("Nuevo Solitario");
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                if(fc.showOpenDialog(open) == JFileChooser.APPROVE_OPTION);
                
                ficheroDestino = fc.getSelectedFile().getAbsolutePath();
                
                loadData(ficheroDestino);
            }
            else if(e.getSource() == salir)
            {
                System.exit(0);
            }
            else if(e.getSource() == deshacer)
            {
                if(decidirFichero == 2)
                {
                    
                }
                else if(decidirFichero % 2 == 0)
                {
                    loadData("Deshacer2.txt");
                }
                else
                {
                    loadData("Deshacer.txt");
                }
            }
            else if(e.getSource() == ayuda)
            {
                Dialog textoAyuda = new Dialog();
                textoAyuda.setVisible(true);
            }
        }
    }
    
    public int EncuentraCarta(int posicion, Pila pila)
    {    
        int posicionControl = 2;
        
        for(int i = 0; i < 52; i++)
        {
            if(pila.getPaloPosicion(posicion) == control[i].getPalo())
            {
                if(pila.getNumeroPosicion(posicion) == control[i].getNumero())
                {
                    posicionControl = i;
                    break;
                }
            }
        }       
        return posicionControl;
    }
    public int EncuentraCartaBaraja(Carta carta)
    {
        int posicionControl = 0;
        
        for(int i = 0; i < 52; i++)
        {
            if(carta.getPalo() == control[i].getPalo())
            {
                if(carta.getNumero() == control[i].getNumero())
                {
                    posicionControl = i;
                    break;
                }
            }
        }
        return posicionControl;
    }
    
    class GeneradorDeCartas extends JLabel implements MouseListener
    {        
        public GeneradorDeCartas()
        {
            ImageIcon helper = new ImageIcon(getClass().getResource("/Imagenes/DorsoDeCarta.jpg"));
            setIcon(helper); //Le ponemos una imagen (de un dorso) para saber dónde clicar exactamente
            addMouseListener(this); //Lo ponemos como listener del mouse para que detecte los clicks
        }
        @Override
        public void mouseClicked(MouseEvent e) //Cada vez que hagamos click sobre el:
        {
            if(contador == limite - 1)
            {
                this.setVisible(false);
                reponer.setVisible(true);
                contador++;
            }           
            if(contador == limite)
            {
                contador = 0;
                               
                baraja = todasLasCartas.devolverBaraja();
                limite = todasLasCartas.numeroCartas();
                repeticion++;
                
            }
            if(repeticion == 0)
            {
                baraja[numerosAleatorios[contador]].setOpaque(true);
                baraja[numerosAleatorios[contador]].setBounds(30, 200, 100, 139);
                panel.add(baraja[numerosAleatorios[contador]], new Integer(0));
                panel.setPosition(baraja[numerosAleatorios[contador]], 1);      //Se generará una carta aleatoria en la posicion indicada
                baraja[numerosAleatorios[contador]].setOrigin(30, 200);         //Marcaremos dicha posicion como origen de la carta para que vuelva ahí cuando arrastremos y no podamos ponerla en ningún sitio (o en un sitio incorrecto)
                panel.moveToFront(baraja[numerosAleatorios[contador]]);         //Indicaremos que ese será el componente que esté arriba
                
                todasLasCartas.cartaSiPrincipal(baraja[numerosAleatorios[contador]]);
                baraja[numerosAleatorios[contador]].addMouseListener(mc);       //A cada carta le daremos la instruccion de ser el componente que esté arriba cuando cliquemos sobre ella
            }
            else
            {    
                baraja[contador].setVisible(true);
                panel.moveToFront(baraja[contador]);    
                System.out.println("Hola");
            }
            
             
            System.out.println("contador: " + contador);  
            
                
            contador++; //Aumentamos el contador para seguir avanzando por el array de aleatorios
            if(decidirFichero % 2 == 0)
            {
                saveData("Deshacer2.txt");
            }
            else
            {
                saveData("Deshacer.txt");
            }
            decidirFichero++;
        }

        @Override
        public void mousePressed(MouseEvent e){};@Override public void mouseReleased(MouseEvent e){};@Override public void mouseEntered(MouseEvent e){};@Override  public void mouseExited(MouseEvent e){};
        
    }
    
    class MueveCapa implements MouseListener //Esta es la clase que se encarga de poner el componente indicado arriba del todo
    {
        @Override
        public void mousePressed(MouseEvent e)
        {
            Carta carta = (Carta) e.getSource();
            Carta[] cartas = new Carta[52];
            int posicionCarta;
            int numeroDeCartas;
            posicionCarta = carta.getPosicion();
            System.out.println("La posicion de la carta es: " + posicionCarta);
            numeroDeCartas = carta.getPilaOrigen(carta.getOrigenX(), carta.getOrigenY()).numeroCartas();
            
            if(posicionCarta == numeroDeCartas - 1)
            {
                panel.moveToFront(carta);
            }
            else if(carta.getPilaOrigen(carta.getOrigenX(), carta.getOrigenY()) == todasLasCartas)
            {
                panel.moveToFront(carta);
            }
            else
            {
                cartas = carta.getPilaOrigen(carta.getOrigenX(), carta.getOrigenY()).devolverBaraja();
                
                for(int i = posicionCarta; i < numeroDeCartas; i++)
                {
                    panel.moveToFront(cartas[i]);
                }
            }
            
        }
        
        @Override
        public void mouseClicked(MouseEvent e){};@Override public void mouseReleased(MouseEvent e){};@Override public void mouseEntered(MouseEvent e){};@Override  public void mouseExited(MouseEvent e){};   
        
    }
    public class Carta extends JLabel implements MouseMotionListener
    {
        int palo = 0;
        int numero = 0;
        int xOrigen = 0; //Estas serán las posiciones que indiquen dónde estará anclada la carta en caso de que la pongamos en un sitio que no sea correcto. Como no podrá ponerse ahí, volverá al origen
        int yOrigen = 0;
        int posicionEnPila = 0; //También podremos saber en qué posicion de la pila se encuentra la carta
        ImageIcon reversoCarta = new ImageIcon(getClass().getResource("/Imagenes/DorsoDeCarta.jpg"));
        int reverso = 0;
        ImageIcon imagenCarta;
    
        String[][] todaCarta = 
        {
            {"/Imagenes/ace_of_spades2.png", "/Imagenes/2_of_spades.png", "/Imagenes/3_of_spades.png", "/Imagenes/4_of_spades.png", "/Imagenes/5_of_spades.png", "/Imagenes/6_of_spades.png", "/Imagenes/7_of_spades.png", "/Imagenes/8_of_spades.png","/Imagenes/9_of_spades.png","/Imagenes/10_of_spades.png", "/Imagenes/jack_of_spades2.png", "/Imagenes/queen_of_spades2.png", "/Imagenes/king_of_spades2.png"},
            {"/Imagenes/ace_of_clubs.png", "/Imagenes/2_of_clubs.png", "/Imagenes/3_of_clubs.png", "/Imagenes/4_of_clubs.png", "/Imagenes/5_of_clubs.png", "/Imagenes/6_of_clubs.png", "/Imagenes/7_of_clubs.png", "/Imagenes/8_of_clubs.png", "/Imagenes/9_of_clubs.png", "/Imagenes/10_of_clubs.png", "/Imagenes/jack_of_clubs2.png", "/Imagenes/queen_of_clubs2.png", "/Imagenes/king_of_clubs2.png"},
            {"/Imagenes/ace_of_hearts.png", "/Imagenes/2_of_hearts.png", "/Imagenes/3_of_hearts.png", "/Imagenes/4_of_hearts.png", "/Imagenes/5_of_hearts.png", "/Imagenes/6_of_hearts.png", "/Imagenes/7_of_hearts.png", "/Imagenes/8_of_hearts.png", "/Imagenes/9_of_hearts.png", "/Imagenes/10_of_hearts.png", "/Imagenes/jack_of_hearts2.png", "/Imagenes/queen_of_hearts2.png", "/Imagenes/king_of_hearts2.png"},
            {"/Imagenes/ace_of_diamonds.png", "/Imagenes/2_of_diamonds.png", "/Imagenes/3_of_diamonds.png", "/Imagenes/4_of_diamonds.png", "/Imagenes/5_of_diamonds.png", "/Imagenes/6_of_diamonds.png", "/Imagenes/7_of_diamonds.png", "/Imagenes/8_of_diamonds.png", "/Imagenes/9_of_diamonds.png", "/Imagenes/10_of_diamonds.png", "/Imagenes/jack_of_hearts2.png", "/Imagenes/queen_of_diamonds2.png", "/Imagenes/king_of_diamonds2.png"}
        };
             
        public Carta(int palo, int numero, int reverso)
        {
            this.palo = palo;
            this.numero = numero;   
            this.reverso = reverso;
            
            imagenCarta = new ImageIcon(getClass().getResource(todaCarta[palo][numero])); //Sabremos qué imagen le corresponde en función del palo y el numero
            
            
            if(reverso == 1)
            {
               setIcon(imagenCarta); 
            }
            else
            {
                setIcon(reversoCarta);
            }
            addMouseMotionListener(this);
            Movimiento movimiento = new Movimiento(this);
            addMouseListener(movimiento); //Le daremos el listener para poder arrastrarla por el panel
        }
        public int getReverso()
        {
            return reverso;
        }
        public void darLaVuelta()
        {
            /*if(getReverso() == 1)
            {
                setIcon(reversoCarta);
            }
            else
            {
                setIcon(imagenCarta);
            }*/
            setIcon(reversoCarta);
            reverso = 0;
        }
        
        public void descubrir()
        {
            setIcon(imagenCarta);
            reverso = 1;
        }
        public void setPosicion(int posicion)
        {
            posicionEnPila = posicion;
        }
        
        public int getPosicion()
        {
            return posicionEnPila;
        }
    
        public void setOrigin(int x, int y)
        {
            xOrigen = x;
            yOrigen = y;
        }
        
        public int getOrigenX()
        {
            return xOrigen;
        }
        
        public int getOrigenY()
        {
            return yOrigen;
        }
    
        public int getPalo()
        {
            return this.palo;
        }
    
        public int getNumero()
        {
            return this.numero;
        }

        @Override
        public void mouseDragged(MouseEvent e) //Aquí modificaremos su posicion en función de cuando la arrastremos
        {
            
            int posicionCarta;
            int numeroDeCartas;
            Carta[] aux = new Carta[13];
            
            posicionCarta = this.getPosicion();
            numeroDeCartas = this.getPilaOrigen(this.getOrigenX(), this.getOrigenY()).numeroCartas();
            if(getReverso() == 1)
            {
                if(posicionCarta == numeroDeCartas - 1)
                {
                    setLocation(this.getX() + e.getX() - this.getWidth() / 2, this.getY() + e.getY() - this.getHeight() / 2);
                }
                else if(this.getPilaOrigen(this.getOrigenX(), this.getOrigenY()) == todasLasCartas)
                {
                    setLocation(this.getX() + e.getX() - this.getWidth() / 2, this.getY() + e.getY() - this.getHeight() / 2);
                }
                else
                {   
                    aux = this.getPilaOrigen(this.getOrigenX(), this.getOrigenY()).devolverBaraja();
                    setLocation(this.getX() + e.getX() - this.getWidth() / 2, this.getY() + e.getY() - this.getHeight() / 5);
                
                    int contador = 1;
                    for(int i = posicionCarta + 1; i < numeroDeCartas; i++)
                    {
                        aux[i].setLocation(this.getX() + e.getX() - this.getWidth() / 2, this.getY() + (30 * contador) + e.getY() - this.getHeight() / 5);
                        contador++;
                    }
                }
            }
            
        }

        @Override
        public void mouseMoved(MouseEvent e) {
       
        }
        
        public Pila getPilaOrigen(int x, int y) //Con este metodo podremos saber en qué pila se encuentra la carta en función del origen de su x
        {
            if(x == 30)
            {
                return todasLasCartas;
            }
            else if(x == 157)
            {
                return primeraPila;
            }
            else if(x == 277)
            {
                return segundaPila;
            }
            else if(x == 407)
            {
                return terceraPila;
            }
            else if(x == 537)
            {
                return cuartaPila;
            }
            else if(x == 667)
            {
                return quintaPila;
            }
            else if(x == 797)
            {
                return sextaPila;
            }
            else if(x == 927)
            {
                return septimaPila;
            }
            else 
            {
                if(y == 30)
                {
                    return pilaDeOros;
                }
                else if(y == 229)
                {
                    return pilaDeEspadas;
                }
                else if(y == 428)
                {
                    return pilaDeBastos;
                }
                else
                {
                    return pilaDeCopas;
                }
            }
        }
    
        class Movimiento implements MouseListener //Esta clase interna nos sirve para tener el método que nos diga cuándo hemos soltado la carta
        {
            Carta carta;
            public Movimiento(Carta carta)
            {
                this.carta = carta;
            }
            @Override
            public void mouseClicked(MouseEvent e){
                //System.out.println("La carta esta en la posicion: " + getPosicion());
                System.out.println("La pila de la carta es: " + getPilaOrigen(xOrigen, yOrigen));
            }
            ;@Override public void mousePressed(MouseEvent e){};@Override public void mouseEntered(MouseEvent e){};@Override  public void mouseExited(MouseEvent e){};
            
            public void comProbarTerminar()
            {
                if(contadorPica == 12 && contadorTrevol == 12 && contadorCorazon == 12 && contadorDiamante == 12)
                {
                    System.out.println("HAASSS GANADO");
                    System.exit(0);
                }
            }
            
            @Override
            public void mouseReleased(MouseEvent e)
            {
                int posicionPrevia = carta.getPosicion();
                int numeroDeCartasPrevio = carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas();
                Pila pilaPrevia = carta.getPilaOrigen(xOrigen, yOrigen);
                Carta[] cartasEnPila = new Carta[52];
                boolean ultimaCarta = true;
                
                if(posicionPrevia != numeroDeCartasPrevio - 1)
                {
                    cartasEnPila = carta.getPilaOrigen(xOrigen, yOrigen).devolverBaraja();
                    ultimaCarta = false;  
                    for(int i = 0; i < numeroDeCartasPrevio; i++)
                    {
                        System.out.println(pilaPrevia.getCarta(i).getPalo());
                    }
                }
                            
                if(getX() >= 140 && getX() < 260) //Si cuando la sueltas está en estas cordenadas, significa que será la primera pila
                {
                    if(primeraPila.AñadirCarta(carta)) 
                    {          
                        if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas) //Si la carta viene de la pila de la baraja, habrá que darle un trato distinto a la hora de eliminarla(En especial si es la segunda vez que generamos las cartas
                        {
                            if(repeticion == 0)
                            {
                                setLocation(157, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(157, 30 *(getPosicion() + 1));    
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            else
                            {
                                setLocation(157, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(157, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                
                            }
                        }
                        else
                        {
                            setLocation(157, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                            carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                            carta.setOrigin(157, 30 *(getPosicion() + 1));
                            if(decidirFichero % 2 == 0)
                            {
                                saveData("Deshacer.txt");
                            }
                            else
                            {
                                saveData("Deshacer2.txt");
                            }
                            decidirFichero++;
                                                       
                            if(ultimaCarta == false)
                            {
                                for(int i = 0; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    System.out.println(pilaPrevia.getCarta(i).getPalo());
                                }
                                for(int i = posicionPrevia; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    Carta cartaAux = pilaPrevia.getCarta(posicionPrevia);
                                    
                                    primeraPila.AñadirCarta(pilaPrevia.getCarta(posicionPrevia));   
                                    pilaPrevia.QuitarCartaBaraja(posicionPrevia);
                                    cartaAux.setLocation(157, 30 * (cartaAux.getPosicion() + 1));
                                    cartaAux.setOrigin(157, 30 * (cartaAux.getPosicion() + 1)); 
                                }
                            }
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            
                        }
                        //Cambiaremos su origen a esas coordenadas para que quede anclada ahí
                       // System.out.println("En la primera pila quedan " + carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas());
                    } 
                    else
                    {
                        setLocation(xOrigen, yOrigen); //Si no se puede poner en estas coordenadas, vuelve al lugar donde estuviese anclada
                        if(ultimaCarta == false)
                        {
                            for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                            {
                                cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                            }
                        }
                    }
                }
                else if(getX() >= 260 && getX() < 390) //Mas de lo mismo, pero esta vez la segunda columna
                {
                    if(segundaPila.AñadirCarta(carta))
                    {
                        if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                        {
                            if(repeticion == 0)
                            {
                                setLocation(277, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(277, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            else
                            {
                                setLocation(277, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(277, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                        }
                        else
                        {
                            setLocation(277, 30 * (getPosicion() + 1));
                            carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia);
                            carta.setOrigin(277, 30 * (getPosicion() + 1));
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            
                            if(ultimaCarta == false)
                            {
                                for(int i = 0; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    System.out.println(pilaPrevia.getCarta(i).getPalo());
                                }
                                for(int i = posicionPrevia; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    Carta cartaAux = pilaPrevia.getCarta(posicionPrevia);
                                    
                                    segundaPila.AñadirCarta(pilaPrevia.getCarta(posicionPrevia));   
                                    pilaPrevia.QuitarCartaBaraja(posicionPrevia);
                                    cartaAux.setLocation(277, 30 * (cartaAux.getPosicion() + 1));
                                    cartaAux.setOrigin(277, 30 * (cartaAux.getPosicion() + 1)); 
                                }
                            }
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                        }
                    }
                    else
                    {
                        setLocation(xOrigen, yOrigen);
                        
                        if(ultimaCarta == false)
                        {
                            for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                            {
                                cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                            }
                        }
                    }
                }
                else if(getX() >= 390 && getX() < 520) //Mas de lo mismo, pero esta vez la tercera columna
                {
                    if(terceraPila.AñadirCarta(carta))
                    {
                        if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                        {
                            if(repeticion == 0)
                            {
                                setLocation(407, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(407, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            else
                            {
                                setLocation(407, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(407, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                        }
                        else
                        {
                            setLocation(407, 30 * (getPosicion() + 1));
                            carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia);
                            carta.setOrigin(407, 30 * (getPosicion() + 1));
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            
                            if(ultimaCarta == false)
                            {
                                for(int i = 0; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    System.out.println(pilaPrevia.getCarta(i).getPalo());
                                }
                                for(int i = posicionPrevia; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    Carta cartaAux = pilaPrevia.getCarta(posicionPrevia);
                                    
                                    terceraPila.AñadirCarta(pilaPrevia.getCarta(posicionPrevia));   
                                    pilaPrevia.QuitarCartaBaraja(posicionPrevia);
                                    cartaAux.setLocation(407, 30 * (cartaAux.getPosicion() + 1));
                                    cartaAux.setOrigin(407, 30 * (cartaAux.getPosicion() + 1)); 
                                }
                            }
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                        }
                        
                       // System.out.println("La posicion de la carta era: " + carta.getPosicion());
                        
                    }
                    else
                    {
                        setLocation(xOrigen, yOrigen);
                        
                        if(ultimaCarta == false)
                        {
                            for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                            {
                                cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                            }
                        }
                    }
                }
                else if(getX() >= 520 && getX() < 650) //Mas de lo mismo, pero esta vez la segunda columna
                {
                    if(cuartaPila.AñadirCarta(carta))
                    {
                        if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                        {
                            if(repeticion == 0)
                            {
                                setLocation(537, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(537, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            else
                            {
                                setLocation(537, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(537, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                        }
                        else
                        {
                            setLocation(537, 30 * (getPosicion() + 1));
                            carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia);
                            carta.setOrigin(537, 30 * (getPosicion() + 1));
                            
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            if(ultimaCarta == false)
                            {
                                for(int i = 0; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    System.out.println(pilaPrevia.getCarta(i).getPalo());
                                }
                                for(int i = posicionPrevia; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    Carta cartaAux = pilaPrevia.getCarta(posicionPrevia);
                                    
                                    cuartaPila.AñadirCarta(pilaPrevia.getCarta(posicionPrevia));   
                                    pilaPrevia.QuitarCartaBaraja(posicionPrevia);
                                    cartaAux.setLocation(537, 30 * (cartaAux.getPosicion() + 1));
                                    cartaAux.setOrigin(537, 30 * (cartaAux.getPosicion() + 1)); 
                                }
                            }
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                        }
                        
                       // System.out.println("La posicion de la carta era: " + carta.getPosicion());
                        
                    }
                    else
                    {
                        setLocation(xOrigen, yOrigen);
                        
                        if(ultimaCarta == false)
                        {
                            for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                            {
                                cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                            }
                        }
                    }
                }
                else if(getX() >= 650 && getX() < 780) //Mas de lo mismo, pero esta vez la segunda columna
                {
                    if(quintaPila.AñadirCarta(carta))
                    {
                        if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                        {
                            if(repeticion == 0)
                            {
                                setLocation(667, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(667, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            else
                            {
                                setLocation(667, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(667, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                        }
                        else
                        {
                            setLocation(667, 30 * (getPosicion() + 1));
                            carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia);
                            carta.setOrigin(667, 30 * (getPosicion() + 1));
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            
                            if(ultimaCarta == false)
                            {
                                for(int i = posicionPrevia; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    Carta cartaAux = pilaPrevia.getCarta(posicionPrevia);
                                    
                                    quintaPila.AñadirCarta(pilaPrevia.getCarta(posicionPrevia));   
                                    pilaPrevia.QuitarCartaBaraja(posicionPrevia);
                                    cartaAux.setLocation(667, 30 * (cartaAux.getPosicion() + 1));
                                    cartaAux.setOrigin(667, 30 * (cartaAux.getPosicion() + 1)); 
                                }
                            }
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                        }
                        
                      //  System.out.println("La posicion de la carta era: " + carta.getPosicion());
                        
                    }
                    else
                    {
                        setLocation(xOrigen, yOrigen);
                        
                        if(ultimaCarta == false)
                        {
                            for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                            {
                                cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                            }
                        }
                    }
                }
                else if(getX() >= 780 && getX() < 910) //Mas de lo mismo, pero esta vez la segunda columna
                {
                    if(sextaPila.AñadirCarta(carta))
                    {
                        if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                        {
                            if(repeticion == 0)
                            {
                                setLocation(797, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(797, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            else
                            {
                                setLocation(797, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(797, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                        }
                        else
                        {
                            setLocation(797, 30 * (getPosicion() + 1));
                            carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia);
                            carta.setOrigin(797, 30* (getPosicion() + 1));
                            
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            
                            if(ultimaCarta == false)
                            {
                                for(int i = 0; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    System.out.println(pilaPrevia.getCarta(i).getPalo());
                                }
                                for(int i = posicionPrevia; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    Carta cartaAux = pilaPrevia.getCarta(posicionPrevia);
                                    
                                    sextaPila.AñadirCarta(pilaPrevia.getCarta(posicionPrevia));   
                                    pilaPrevia.QuitarCartaBaraja(posicionPrevia);
                                    cartaAux.setLocation(797, 30 * (cartaAux.getPosicion() + 1));
                                    cartaAux.setOrigin(797, 30 * (cartaAux.getPosicion() + 1)); 
                                }
                            }
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                        }
                       // System.out.println("La posicion de la carta era: " + carta.getPosicion());
                        
                    }
                    else
                    {
                        setLocation(xOrigen, yOrigen);
                        
                        if(ultimaCarta == false)
                        {
                            for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                            {
                                cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                            }
                        }
                    }
                }
                else if(getX() >= 910 && getX() < 1040) //Mas de lo mismo, pero esta vez la segunda columna
                {
                    if(septimaPila.AñadirCarta(carta))
                    {
                        if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                        {
                            if(repeticion == 0)
                            {
                                setLocation(927, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(927, 30 *(getPosicion() + 1));
                                
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            else
                            {
                                setLocation(927, 30 * (getPosicion() + 1)); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                carta.setOrigin(927, 30 *(getPosicion() + 1));
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                        }
                        else
                        {
                            setLocation(927, 30 * (getPosicion() + 1));
                            carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia);
                            carta.setOrigin(927, 30 * (getPosicion() + 1));
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            
                            if(ultimaCarta == false)
                            {
                                for(int i = 0; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    System.out.println(pilaPrevia.getCarta(i).getPalo());
                                }
                                for(int i = posicionPrevia; i < numeroDeCartasPrevio - 1; i++)
                                {
                                    Carta cartaAux = pilaPrevia.getCarta(posicionPrevia);
                                    
                                    septimaPila.AñadirCarta(pilaPrevia.getCarta(posicionPrevia));   
                                    pilaPrevia.QuitarCartaBaraja(posicionPrevia);
                                    cartaAux.setLocation(927, 30 * (cartaAux.getPosicion() + 1));
                                    cartaAux.setOrigin(927, 30 * (cartaAux.getPosicion() + 1)); 
                                }
                            }
                            if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                        }
                        //System.out.println("La posicion de la carta era: " + carta.getPosicion());
                        
                    }
                    else
                    {
                        setLocation(xOrigen, yOrigen);
                        
                        if(ultimaCarta == false)
                        {
                            for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                            {
                                cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                            }
                        }
                    }
                }
                else if(getX() >= 1040 && getX() < 1170)
                {
                    if(getY() >= 0 && getY() < 199)
                    {
                        if(pilaDeOros.CompletarPalo(carta))
                        {
                            if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                            {
                                if(repeticion == 0)
                                {
                                    setLocation(1057, 30); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 30);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                                else
                                {
                                    setLocation(1057, 30); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 30);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                            }
                            else
                            {
                                setLocation(1057, 30);
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta();
                                carta.setOrigin(1057, 30);
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }                           
                            contadorPica++;
                        }
                        else
                        {
                            setLocation(xOrigen, yOrigen);
                            
                            if(ultimaCarta == false)
                            {
                                for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                                {
                                    cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                                }
                            }
                        }
                    }
                    else if(getY() >= 199 && getY() < 398)
                    {
                        if(pilaDeEspadas.CompletarPalo(carta))
                        {
                            if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                            {
                                if(repeticion == 0)
                                {
                                    setLocation(1057, 229); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 229);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                                else
                                {
                                    setLocation(1057, 229); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 229);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                            }
                            else
                            {
                                setLocation(1057, 229);
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta();
                                carta.setOrigin(1057, 229);
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            contadorTrevol++;
                        }
                        else
                        {
                            setLocation(xOrigen, yOrigen);
                            
                            if(ultimaCarta == false)
                            {
                                for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                                {
                                    cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                                }
                            }
                        }
                    }
                    else if(getY() >= 398 && getY() < 597)
                    {
                        if(pilaDeBastos.CompletarPalo(carta))
                        {
                            if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                            {
                                if(repeticion == 0)
                                {
                                    setLocation(1057, 428); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 428);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                                else
                                {
                                    setLocation(1057, 428); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 428);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                            }
                            else
                            {
                                setLocation(1057, 428);
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta();
                                carta.setOrigin(1057, 428);
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            contadorCorazon++;
                        }
                        else
                        {
                            setLocation(xOrigen, yOrigen);
                            if(ultimaCarta == false)
                            {
                                for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                                {
                                    cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                                }
                            }
                        }
                    }
                    
                    else if(getY() >= 597 && getY() < 796)
                    {
                        if(pilaDeCopas.CompletarPalo(carta))
                        {
                            if(carta.getPilaOrigen(xOrigen, yOrigen) == todasLasCartas)
                            {
                                if(repeticion == 0)
                                {
                                    setLocation(1057, 627); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta(); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 627);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                                else
                                {
                                    setLocation(1057, 627); //Si se puede, pondremos la carta en el lugar de la pila que le corresponde
                                    carta.getPilaOrigen(xOrigen, yOrigen).QuitarCartaBaraja(posicionPrevia); //Quitaremos la carta de la pila en la que estuviera
                                    carta.setOrigin(1057, 627);
                                    if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                                }
                            }
                            else
                            {
                                setLocation(1057, 627);
                                carta.getPilaOrigen(xOrigen, yOrigen).QuitarCarta();
                                carta.setOrigin(1057, 627);
                                if(decidirFichero % 2 == 0)
                                {
                                    saveData("Deshacer.txt");
                                }
                                else
                                {
                                    saveData("Deshacer2.txt");
                                }
                                decidirFichero++;
                            }
                            contadorDiamante++;
                        }
                        else
                        {
                            setLocation(xOrigen, yOrigen);
                            if(ultimaCarta == false)
                            {
                                for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                                {
                                    cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                                }
                            }
                        }
                    }
                    else
                    {
                        setLocation(xOrigen, yOrigen);
                        if(ultimaCarta == false)
                            {
                                for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                                {
                                    cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                                }
                            }
                    }
                    
                }
                else
                {
                    setLocation(xOrigen, yOrigen);
                    if(ultimaCarta == false)
                    {
                        for(int i = posicionPrevia + 1; i < carta.getPilaOrigen(xOrigen, yOrigen).numeroCartas(); i++)
                        {
                            cartasEnPila[i].setLocation(cartasEnPila[i].getOrigenX(), cartasEnPila[i].getOrigenY());
                        }
                    }
                }
            }            
        }
    } 
    
    public class Pila
    {
        Carta[] columnaDeCartas = new Carta[52]; //Carta1 //Cada pila tendrá una cantidad de cartas posibles
    
        int contadorDeCartas = 0; //Este es el contador que definirá en qué posicion va cada carta en la pila
        int palo;
        
        public Pila(int numero)
        {
            palo = numero;
        }
    
        public Carta[] devolverBaraja()
        {
            return columnaDeCartas;
        }
        
        public void cartaSiPrincipal(Carta carta)
        {
            columnaDeCartas[contadorDeCartas] = carta;
            carta.setPosicion(contadorDeCartas);
            contadorDeCartas++;
        }
        
        public int numeroCartas()
        {
            return contadorDeCartas;
        }
        
        public int getPaloPosicion(int posicion)
        {
            return columnaDeCartas[posicion].getPalo();
        }
        
        public int getNumeroPosicion(int posicion)
        {
            return columnaDeCartas[posicion].getNumero();
        }
        
        public Carta getCarta(int posicion)
        {
            return columnaDeCartas[posicion];
        }
                
        public void vaciarPila()
        {
            for(int i = 0; i < 52; i++)
            {
                columnaDeCartas[i] = null;
            }
            contadorDeCartas = 0;
        }
        public boolean AñadirCarta(Carta carta)
        {
            boolean colocada = false;
        
            if(confirmarPosicion(carta)) //Si se puede colocar la carta
            {               
                colocada = true;
                columnaDeCartas[contadorDeCartas] = carta;
                carta.setPosicion(contadorDeCartas); //Le indicamos a la propia carta su posicion en la pila
                 //Aumentamos el contador para indicar la siguiente posicion disponible
                contadorDeCartas++;
            }
            return colocada;
        }
        public void QuitarCarta() //Para cuando movamos una carta de pila a otra y tengamos que eliminar la carta de nuestro array en la pila
        {           
            //System.out.println("Quitando la carta en la posicion: " + contadorDeCartas);
            columnaDeCartas[contadorDeCartas - 1] = null;
            contadorDeCartas --; //Decrementamos el contador indicando la nueva posicion disponible
            if(contadorDeCartas > 0)
            {
                columnaDeCartas[contadorDeCartas - 1].descubrir();
            }
        }
        
        public void QuitarCartaBaraja(int posicion)
        {
            columnaDeCartas[posicion] = null;
            
            if(contadorDeCartas - 1 == posicion)
            {
                columnaDeCartas[contadorDeCartas- 1] = null;
                contadorDeCartas--;
            }
            else
            {
                for(int i = posicion; i < 51; i++)
                {
                    columnaDeCartas[i] = columnaDeCartas[i + 1];
                    columnaDeCartas[i + 1] = null;
                }
                limite--;
                contadorDeCartas--;
            }  
            for(int i = 0; i < contadorDeCartas; i++)
            {
                columnaDeCartas[i].setPosicion(i);
            }
            if(contadorDeCartas > 0)
            {
                columnaDeCartas[contadorDeCartas - 1].descubrir();
            }
        }
            
        public boolean confirmarPosicion(Carta carta /*Carta2*/)
        {
            boolean sePuede = false;
        
            int contador = -1;
            for(int i = 0; i < 52; i++)
            {
                if(columnaDeCartas[i] != null) //Recorremos la pila en busca de la última posicion disponible.
                {
                    contador++;
                }
            }
            //System.out.println("Se han encontrado " + contador + "CARTas");
            if(contador == -1) //Si no hay ninguna carta en la pila, solo se podrá colocar un rey
            {
                if(carta.getNumero() == 12) 
                {
                    sePuede = true;
                }
            }
            else
            {
                sePuede = MovimientoPosible(columnaDeCartas[contador], carta);    
            }
        
            return sePuede;
        }
        public boolean MovimientoPosible(Carta carta1, Carta carta2) //La carta solo se podrá poner si el palo y el numero son adecuados.
        {
            if(carta1.getPalo() == 0 || carta1.getPalo() == 1)
            {               
                if(carta2.getPalo() == 0 || carta2.getPalo() == 1)
                {
                    return false;
                }
                if(carta1.getNumero() != carta2.getNumero() + 1)
                {
                    return false;
                }
            }
            else if(carta1.getPalo() == 2 || carta1.getPalo() == 3)
            {
                if(carta2.getPalo() == 2 || carta2.getPalo() == 3)
                {
                    return false;
                }
                if(carta1.getNumero() != carta2.getNumero() + 1)
                {
                    return false;
                }
            }      
            return true;
        }
        public boolean CompletarPalo(Carta carta)
        {
            boolean sePuede = true;
            
            if(carta.getPalo() != palo)
            {
                return false;
            }
            
            if(contadorDeCartas == 0)
            {
                if(carta.getNumero() != 0)
                {
                    return false;
                }
            }
            else
            {
                if(carta.getNumero() - 1 != columnaDeCartas[contadorDeCartas - 1].getNumero())
                {
                    return false;
                }
            }
            columnaDeCartas[contadorDeCartas] = carta;
            contadorDeCartas ++;
            
            return sePuede;
        }
    }
}