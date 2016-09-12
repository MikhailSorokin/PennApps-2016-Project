<script>

class room{
int volume;
boolean light;
boolean vibration;
String status;
	constructor(vibration, volume, light){
		this.vibration = vibration;
		this.volume = volume;
		this.light = light;
	}

	function getStatus(string){
		if(room.vibration == true && room.light == true && room.volume > 135)
		{
		room.status == "Not Empty, and quite active";
		}
		else if(room.vibration == false && room.light == true && room.volume > 135)
		{
		room.status == "Not Empty, but not active";
		}

		elseif(room.vibration == false && room.volume < 100 && room.light == false)
		{
		room.status == "Empty";
		}
		elseif(room.volume > 135 && room.light == true)
		{
		room.status == "Occupied, but not loud";
		}
		else if(room.volume < 100 && room.light == false)
		{
		room.status = "Empty";
		}
		elseif(room.vibration == true && room.volume < 135)
		{
		room.status = "Active, but quiet"
		}
		else{
			room.status = "Not empty"
			}
		return room.status;
	}

}

	function main(){
		var status = room.getStatus;
		}

</script>
