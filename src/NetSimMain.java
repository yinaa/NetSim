public class NetSimMain {
    //... Create model, view, and controller.  They are
    //    created once here and passed to the parts that
    //    need them so there is only one copy of each.
    public static void main(String[] args) {        
        NetSimModel      model      = new NetSimModel();
        NetSimView       view       = new NetSimView(model);
 //       NetSimController controller = new NetSimController(model, view);
        
        view.setVisible(true);
        view.setLocationRelativeTo(null);
    }
}
