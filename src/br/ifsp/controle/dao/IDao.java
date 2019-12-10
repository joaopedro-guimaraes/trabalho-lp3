/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.controle.dao;

import java.util.List;

/**
 *
 * @author Thon
 */
public interface IDao<T> {
    
    public boolean create(T aux);
    public T read(int id);    
    public List<T> readAll(boolean status, String str);
    public List<T> readAll(String str);
    public boolean update(T aux);
    public boolean delete(int id);
}
