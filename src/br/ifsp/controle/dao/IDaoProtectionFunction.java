/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.controle.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author IFSP
 */
@FunctionalInterface
public interface IDaoProtectionFunction<T> {
    public PreparedStatement execute(PreparedStatement stmp, T aux );    
}
