package Modules;

public class Runway {
		private static int id=0;
		
		private int runwayId;
		private Plane plane;
		
		
		public Runway() {
			super();
			this.setRunwayId(id++);
			this.plane=null;
		}
		
		public Plane getPlane() {
			return plane;
		}
		public void setPlane(Plane plane) {
			this.plane = plane;
		}

		public int getRunwayId() {
			return runwayId;
		}

		public void setRunwayId(int runwayId) {
			this.runwayId = runwayId;
		}
		
		

}
