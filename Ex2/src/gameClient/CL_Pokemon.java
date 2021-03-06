package gameClient;
import api.edge_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

import java.util.Comparator;

public class CL_Pokemon implements Comparator {
	private edge_data _edge;
	private double _value;
	private int _type;
	private Point3D _pos;
	private double min_dist;
	private int min_ro;
	
	public CL_Pokemon(Point3D p, int t, double v, double s, edge_data e)  {
		_type = t;
	//	_speed = s;
		_value = v;
		set_edge(e);
		_pos = p;
		min_dist = -1;
		min_ro = -1;
	}
	public static CL_Pokemon init_from_json(String json) {
		CL_Pokemon ans = null;
		try {
			JSONObject p = (JSONObject) new JSONObject(json).get("Pokemon");
			String arr [] = p.getString("pos").split(",");
			ans = new CL_Pokemon(new Point3D(Double.parseDouble(arr[0]),Double.parseDouble(arr[1]),Double.parseDouble(arr[2])),p.getInt("type"),p.getDouble("value"),0,null);

		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return ans;
	}
	public String toString() {return "F:{v="+_value+", t="+_type+"}";}
	public edge_data get_edge() {
		return _edge;
	}

	public void set_edge(edge_data _edge) {
		this._edge = _edge;
	}

	public Point3D getLocation() {
		return _pos;
	}
	public int getType() {return _type;}
//	public double getSpeed() {return _speed;}
	public double getValue() {return _value;}

	public double getMinDist() {
		return min_dist;
	}

	public void setMinDist(double mid_dist) {
		this.min_dist = mid_dist;
	}

	public int getMin_ro() {
		return min_ro;
	}

	public void setMin_ro(int min_ro) {
		this.min_ro = min_ro;
	}

	@Override
	public int compare(Object o1, Object o2) {
		CL_Pokemon p1 = (CL_Pokemon) o1;
		CL_Pokemon p2 = (CL_Pokemon) o2;
		return Double.compare(p1.getMinDist(),p2.getMinDist());
	}
}
