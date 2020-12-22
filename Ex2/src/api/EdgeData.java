package api;

// class that implements edge_data which represent a edge in the graph
public class EdgeData implements edge_data{
    //class private variables
        private int src;
    private int dest;
    private double weight;
    private String info;
    private int tag;


    public EdgeData(int src, int dest, double weight, String info, int tag) {
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.info = info;
        this.tag = tag;
    }

    //function that return the edge's src
    @Override
    public int getSrc() {
        return this.src;
    }

    //function that return the edge's dest
    @Override
    public int getDest() {
        return this.dest;
    }

    //function that return the edge's weight
    @Override
    public double getWeight() {
        return this.weight;
    }


    public void setWeight(double w) {

        if(w>0)
        {
            this.weight = w;
        }
        else{
            throw new ArithmeticException("Weight must be greater than 0");
        }
    }

    //function that return the edge's info
    @Override
    public String getInfo() {
        return this.info;
    }

    //function that set the edge's src to a given string
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    //function that return the edge's tag
    @Override
    public int getTag() {
        return this.tag;
    }

    //function that set the edge's tag to a given int
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    public class WrapedEdgeData{
        private int src;
        private int dest;
        private double w;

        public WrapedEdgeData(){
            src = EdgeData.this.src;
            dest = EdgeData.this.dest;
            w = EdgeData.this.weight;
        }

        public int getSrc() {
            return src;
        }

        public int getDest() {
            return dest;
        }

        public double getW() {
            return w;
        }
    }
}
