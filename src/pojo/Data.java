/**
  * Copyright 2024 bejson.com 
  */
package pojo;
import java.util.List;

/**
 * Auto-generated: 2024-04-02 22:49:2
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class Data {

    private List<Goods> goods;
    private int auspicious_max_beans;
    private List<Knapsack> knapsack;
    private List<Fanclub_gift> fanclub_gift;
    private List<Mk_gift> mk_gift;
    public void setGoods(List<Goods> goods) {
         this.goods = goods;
     }
     public List<Goods> getGoods() {
         return goods;
     }

    public void setAuspicious_max_beans(int auspicious_max_beans) {
         this.auspicious_max_beans = auspicious_max_beans;
     }
     public int getAuspicious_max_beans() {
         return auspicious_max_beans;
     }

    public void setKnapsack(List<Knapsack> knapsack) {
         this.knapsack = knapsack;
     }
     public List<Knapsack> getKnapsack() {
         return knapsack;
     }

    public void setFanclub_gift(List<Fanclub_gift> fanclub_gift) {
         this.fanclub_gift = fanclub_gift;
     }
     public List<Fanclub_gift> getFanclub_gift() {
         return fanclub_gift;
     }

    public void setMk_gift(List<Mk_gift> mk_gift) {
         this.mk_gift = mk_gift;
     }
     public List<Mk_gift> getMk_gift() {
         return mk_gift;
     }



}