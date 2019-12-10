/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.ifsp.controle.dao;

import java.sql.ResultSet;

/**
 *
 * @author IFSP
 */
@FunctionalInterface
public interface IDaoGetFunction<T> {
    public T executeGet(ResultSet rs, T aux);
}
