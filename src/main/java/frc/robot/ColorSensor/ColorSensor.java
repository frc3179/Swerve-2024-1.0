package frc.robot.ColorSensor;

import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ColorSensor extends SubsystemBase{
    private I2C sensor;
    private ByteBuffer buf = ByteBuffer.allocate(5);
    
    public ColorSensor(I2C.Port port) {
   	 sensor = new I2C(port, 0x39); //port, I2c address    

   	 sensor.write(0x00, 0b00000011); //power on, color sensor on
    }
    
    public int red() {
   	 //reads the address 0x16, 2 bytes of data, gives it to the buffer
   	 sensor.read(0x16, 3, buf);
   	 return buf.get(0);
    }
    
    public int green() {
   	 sensor.read(0x18, 2, buf);
   	 return buf.get(0);
    }
    
    public int blue() {
   	 sensor.read(0x1a, 2, buf);
   	 return buf.get(0);
    }
}
