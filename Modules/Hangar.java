package Modules;

public class Hangar {
		private static int id=0;
		
		private int hangarId;
		private Plane plane;
		
		
		public Hangar() {
			super();
			this.hangarId=id++;
			this.plane=null;
		}
		public int getHangarId() {
			return hangarId;
		}
		public void setHangarId(int hangarId) {
			this.hangarId = hangarId;
		}
		public Plane getPlane() {
			return plane;
		}
		public void setPlane(Plane plane) {
			this.plane = plane;
		}
		
		
}
