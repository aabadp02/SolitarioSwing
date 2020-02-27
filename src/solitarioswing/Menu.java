package solitarioswing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

public class Menu
{
    public static void main(String[] args)
    {
        miMenu menuJuego = new miMenu();
        menuJuego.setVisible(true);
    }
}

class miMenu extends JFrame implements MouseListener
{
    JButton nuevaPartida = new JButton("Nueva Partida");
    JButton cargarPartida = new JButton("Cargar Partida");
    JButton salir = new JButton("Salir");
    JButton solitarioClasico = new JButton("Clásico");
    JButton saltosIzquierda = new JButton("Saltos");
    JButton atras = new JButton("Atras");
    
    
    JLayeredPane pano = new JLayeredPane();
    JLayeredPane panoNuevaPartida = new JLayeredPane();
    SolitarioSwing nuevoSolitario;
    
    ImageIcon imagenNuevaPartida = new ImageIcon(getClass().getResource("/Imagenes/FondoMenuBlur.jpg"));
    JLabel fondoNuevaPartida = new JLabel(imagenNuevaPartida);
    
    ImageIcon imagenFondo = new ImageIcon(getClass().getResource("/Imagenes/FondoMenu.jpg"));              
    JLabel imagenDeFondo = new JLabel(imagenFondo);
    
    Font obj = new Font("Bahnschrift", Font.BOLD, 14);
    
    
    public miMenu()
    {
        setBounds(500, 500, 800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SOLITATIO");
        setResizable(false);
        
       //----------------------------PRIMER PANEL-------------------------------------------

        pano.setPreferredSize(new Dimension(800, 600));
        getContentPane().add(pano);
        
        Toolkit miPantalla = Toolkit.getDefaultToolkit();
        Image logo = miPantalla.getImage(getClass().getResource("/Imagenes/PosibleLogo.jpg"));
        setIconImage(logo);
               
        imagenDeFondo.setOpaque(true);
        imagenDeFondo.setBounds(0, 0, 800, 600);
        pano.add(imagenDeFondo, new Integer(0));
        pano.setPosition(imagenDeFondo, 0);

        pano.setOpaque(true);
        pano.setVisible(true);
        
        fondoNuevaPartida.setOpaque(true);
        fondoNuevaPartida.setBounds(0, 0, 800, 600);
        pano.add(fondoNuevaPartida, new Integer(0));
        pano.setPosition(fondoNuevaPartida, 0);
        fondoNuevaPartida.setVisible(false);
        
        nuevaPartida.setOpaque(true);
        nuevaPartida.setBounds(400, 200, 150, 30);
        nuevaPartida.setBackground(new Color(230, 232, 237));
        nuevaPartida.setForeground(new Color(50, 51, 64));
        nuevaPartida.setFont(obj);
        nuevaPartida.setFocusPainted(false);
        pano.add(nuevaPartida, new Integer(0));
        pano.setPosition(nuevaPartida, 0);
        
        nuevaPartida.addMouseListener(this);
        
        cargarPartida.setOpaque(true);
        cargarPartida.setBounds(400, 270, 150, 30);
        cargarPartida.setBackground(new Color(230, 232, 237));
        cargarPartida.setForeground(new Color(50, 51, 64));
        cargarPartida.setFont(obj);
        cargarPartida.setFocusPainted(false);
        pano.add(cargarPartida, new Integer(0));
        pano.setPosition(cargarPartida, 0);
        
        cargarPartida.addMouseListener(this);
        
        salir.setOpaque(true);
        salir.setBounds(400, 340, 150, 30);
        salir.setBackground(new Color(230, 232, 237));
        salir.setForeground(new Color(50, 51, 64));
        salir.setFont(obj);
        salir.setFocusPainted(false);
        pano.add(salir, new Integer(0));
        pano.setPosition(salir, 0);
        
        salir.addMouseListener(this);
        
        //------------------------------------------------------------------------------------
      
        solitarioClasico.setOpaque(true);
        solitarioClasico.setVisible(false);
        solitarioClasico.setBounds(400, 200, 150, 30);
        solitarioClasico.setBackground(new Color(50, 51, 64));
        solitarioClasico.setForeground(Color.white);
        solitarioClasico.setFont(obj);
        solitarioClasico.setFocusPainted(false);
        pano.add(solitarioClasico, new Integer(0));
        pano.setPosition(solitarioClasico, 0);
        
        solitarioClasico.addMouseListener(this);
        
        saltosIzquierda.setOpaque(true);
        saltosIzquierda.setVisible(false);
        saltosIzquierda.setBounds(400, 270, 150, 30);
        saltosIzquierda.setBackground(new Color(50, 51, 64));
        saltosIzquierda.setForeground(Color.white);
        saltosIzquierda.setFont(obj);
        saltosIzquierda.setFocusPainted(false);
        pano.add(saltosIzquierda, new Integer(0));
        pano.setPosition(saltosIzquierda, 0);
        
        saltosIzquierda.addMouseListener(this);
        
        atras.setOpaque(true);
        atras.setVisible(false);
        atras.setBounds(400, 340, 150, 30);
        atras.setBackground(new Color(50, 51, 64));
        atras.setForeground(Color.white);
        atras.setFont(obj);
        atras.setFocusPainted(false);
        pano.add(atras, new Integer(0));
        pano.setPosition(atras, 0);
        
        atras.addMouseListener(this);
        
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {       
        if(e.getSource() == nuevaPartida)
        {
            nuevaPartida.setVisible(false);
            cargarPartida.setVisible(false);
            salir.setVisible(false);
            imagenDeFondo.setVisible(false);
            
            solitarioClasico.setVisible(true);
            saltosIzquierda.setVisible(true);
            atras.setVisible(true);
            fondoNuevaPartida.setVisible(true);
        }
        else if(e.getSource() == cargarPartida)
        {
            System.out.println("No hay partidas");
        }
        else if(e.getSource() == atras)
        {
            nuevaPartida.setVisible(true);
            cargarPartida.setVisible(true);
            salir.setVisible(true);
            imagenDeFondo.setVisible(true);
            
            solitarioClasico.setVisible(false);
            saltosIzquierda.setVisible(false);
            atras.setVisible(false);
            fondoNuevaPartida.setVisible(false);
            
        }
        else if(e.getSource() == solitarioClasico)
        {
            nuevoSolitario = new SolitarioSwing();
        }
        else
        {
            System.exit(0);
        }
            
    }

    @Override
    public void mousePressed(MouseEvent e) {
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
}