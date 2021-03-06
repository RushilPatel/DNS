package org.dns.packet.section;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.dns.packet.section.record.Record;
import org.dns.packet.abstraction.IAdditional;
import org.dns.packet.abstraction.record.IRecord;

public class Additional implements IAdditional{
    
private Record [] records; //resource records
    

    public Additional(Record [] records){
    	this.setRecords(records);
    }
    
    public Additional(int arcount, byte [] data) throws IOException{
    	ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream (bis);
        readFromInputStream (arcount, dis);
    }
    
    public Additional (int arcount, DataInputStream dis) throws IOException{
    	readFromInputStream (arcount, dis);
    }

    /* (non-Javadoc)
     * @see org.dns.interfaces.IAnswer#readFromInputStream(int, java.io.DataInputStream)
     */
    @Override
    public void readFromInputStream(int arcount, DataInputStream dis) throws IOException{
        Record [] recs = new Record [arcount];
        for(int i = 0; i < arcount; i++){
            recs[i] = new Record (dis);
        }
        this.setRecords(recs);
    }

    /* (non-Javadoc)
     * @see org.dns.interfaces.IAnswer#writeToOutputStream(java.io.DataOutputStream)
     */
    @Override
    public void writeToOutputStream (DataOutputStream dos) throws IOException {
    	Record [] recs = (Record[]) this.getRecords();
        for(int i = 0; i < recs.length; i++){
            dos.write (recs[i].toByteArray ());
        }
    }

    /* (non-Javadoc)
     * @see org.dns.interfaces.IAnswer#toByteArray()
     */
    @Override
    public byte[] toByteArray () throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream ();
        DataOutputStream dos = new DataOutputStream (bos);
        this.writeToOutputStream (dos);
        dos.flush ();
        return bos.toByteArray ();
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    public String toString(){
    	Record [] recs = (Record[]) this.getRecords();
        StringBuilder sb = new StringBuilder ();
        for(int i = 0; i < recs.length; i++){
            sb.append ("\n");
            sb.append ("Record " + (i+1) + ": \n");
            sb.append (recs[i].toString () + "\n");
        }
        return sb.toString ();
    }

	/* (non-Javadoc)
	 * @see org.dns.interfaces.org.dns.packet.IAnswer#setRecords(org.dns.interfaces.org.dns.packet.section.record.IRecord[])
	 */
	@Override
	public void setRecords(IRecord[] records) {
		this.records = (Record[]) records;
	}

	/* (non-Javadoc)
	 * @see org.dns.interfaces.org.dns.packet.IAnswer#getRecords()
	 */
	@Override
	public IRecord[] getRecords() {
		return this.records;
	}
    
}
