
package com.wanyos.modelo.dao;

import java.sql.Connection;

/**
 *
 * @author wanyos
 */
public class MySqlCambiosDao extends MySqlAbstract {
    
     private final Connection cx;
     
     public MySqlCambiosDao(Connection cx){
         this.cx = cx;
     }
    
}
