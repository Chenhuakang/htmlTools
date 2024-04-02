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
    private List<Auspicious> auspicious;
    private int auspicious_max_beans;
    private String rich_beans;
    private List<Knapsack> knapsack;
    private List<Blindbox> blindbox;
    private List<Fanclub_gift> fanclub_gift;
    private List<Mk_gift> mk_gift;
    private List<String> special_gift;
    public void setGoods(List<Goods> goods) {
         this.goods = goods;
     }
     public List<Goods> getGoods() {
         return goods;
     }

    public void setAuspicious(List<Auspicious> auspicious) {
         this.auspicious = auspicious;
     }
     public List<Auspicious> getAuspicious() {
         return auspicious;
     }

    public void setAuspicious_max_beans(int auspicious_max_beans) {
         this.auspicious_max_beans = auspicious_max_beans;
     }
     public int getAuspicious_max_beans() {
         return auspicious_max_beans;
     }

    public void setRich_beans(String rich_beans) {
         this.rich_beans = rich_beans;
     }
     public String getRich_beans() {
         return rich_beans;
     }

    public void setKnapsack(List<Knapsack> knapsack) {
         this.knapsack = knapsack;
     }
     public List<Knapsack> getKnapsack() {
         return knapsack;
     }

    public void setBlindbox(List<Blindbox> blindbox) {
         this.blindbox = blindbox;
     }
     public List<Blindbox> getBlindbox() {
         return blindbox;
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

    public void setSpecial_gift(List<String> special_gift) {
         this.special_gift = special_gift;
     }
     public List<String> getSpecial_gift() {
         return special_gift;
     }

}