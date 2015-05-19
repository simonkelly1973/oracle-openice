package org.mdpnp.hiberdds.testapp;

import java.io.Serializable;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.hibernate.Session;
import org.mdpnp.hiberdds.db.Manager;
import org.mdpnp.hiberdds.mappings.Numeric;
import org.mdpnp.hiberdds.mappings.NumericLifeCycle;
import org.mdpnp.hiberdds.mappings.NumericSample;
import org.mdpnp.hiberdds.mappings.VitalValues;
import org.mdpnp.hiberdds.mappings.VitalValuesDTO;
import org.mdpnp.hiberdds.util.HibernateUtil;

import com.rti.dds.infrastructure.InstanceHandle_t;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//test_w_annotations();
		//test_ValueORA_mappingfile();
		
		testOneToMany();
	    System.out.println("Done");

	}
	
	private static final Base64.Encoder b64Encoder = Base64.getEncoder();
	private static String randomInstanceHandle() {
	    UUID uuid = UUID.randomUUID();
	    byte[] bytes = new byte[16];
	    
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        
        for (int i=0; i<8; i++) {
            msb = bytes[i] & 0xff;
            msb >>= 8;
        }
        for (int i=8; i<16; i++) {
            lsb = bytes[i] & 0xff;
            lsb >>= 8;
        }
        return b64Encoder.encodeToString(bytes);
	}
	
	
	//TODO: This test should be made differently. Use JUNIT to test.
	private static void testOneToMany(){
		//create test numeric topic
		Numeric numericTopic = new Numeric(); 
		numericTopic.setUnique_device_identifier("test_FIVOQQm6kjMY6arhvxJZ7CwzEX24BSi");
		numericTopic.setMetric_id("MDC_PULS_OXIM_PULS_RATE");
		numericTopic.setVendor_metric_id("vendor_metric_ID");
		numericTopic.setInstance_id(0);
		numericTopic.setUnit_id("unit_ID");
		numericTopic.setInstance_handle(randomInstanceHandle());
		
		//create test numeric samples
		NumericSample numericSample1 = new NumericSample();
		Date d1 = new Date();
		numericSample1.setValue(60);
		numericSample1.setDevice_time(d1);
		numericSample1.setPresentation_time(d1);
		numericSample1.setSource_time(d1);	
		
		NumericSample numericSample2 = new NumericSample();
		Date d2 = new Date();
		numericSample2.setValue(70);
		numericSample2.setDevice_time(d2);
		numericSample2.setPresentation_time(d2);
		numericSample2.setSource_time(d2);
		
		//create demo numeric lifecycle
		NumericLifeCycle numericLifecycle = new NumericLifeCycle();
		numericLifecycle.setAlive(1);
		numericLifecycle.setTime(new Date());
		//
	
		Session session = HibernateUtil.getSessionFactory().openSession();	 
		session.beginTransaction();
		session.save(numericTopic);
		
		numericSample1.setNumeric(numericTopic);
		numericSample2.setNumeric(numericTopic);
		numericLifecycle.setNumeric(numericTopic);

//		numericTopic.getNumericSamples().add(numericSample1);
//		numericTopic.getNumericSamples().add(numericSample2);
//		numericTopic.getNumericLifeCyleCol().add(numericLifecycle);
		
		session.save(numericSample1);
		session.save(numericSample2);
		session.save(numericLifecycle);
		
	    session.getTransaction().commit();
	    session.close();
	    HibernateUtil.getSessionFactory().close();
	}
	
	private static void testOneToMany_2(){
		
		Session session = HibernateUtil.getSessionFactory().openSession();	 
		session.beginTransaction();
		//create test numeric samples
		NumericSample numericSample1 = new NumericSample();
		Date d1 = new Date();
		numericSample1.setValue(60);
		numericSample1.setDevice_time(d1);
		numericSample1.setPresentation_time(d1);
		numericSample1.setSource_time(d1);
		session.save(numericSample1);
		
		NumericSample numericSample2 = new NumericSample();
		Date d2 = new Date();
		numericSample2.setValue(70);
		numericSample2.setDevice_time(d2);
		numericSample2.setPresentation_time(d2);
		numericSample2.setSource_time(d2);
		session.save(numericSample2);
		
		//create demo numeric lifecycle
		NumericLifeCycle numericLifecycle = new NumericLifeCycle();
		numericLifecycle.setAlive(1);
		numericLifecycle.setTime(new Date());
		session.save(numericLifecycle);
		
		session.flush();//Is this the key to this approach?
		
		//create test numeric topic
		Numeric numericTopic = new Numeric(); 
		numericTopic.setUnique_device_identifier("test_FIVOQQm6kjMY6arhvxJZ7CwzEX24BSi");
		numericTopic.setMetric_id("MDC_PULS_OXIM_PULS_RATE");
		numericTopic.setVendor_metric_id("vendor_metric_ID");
		numericTopic.setInstance_id(0);
		numericTopic.setUnit_id("unit_ID");
//		numericTopic.setInstance_handle(null);

//		numericTopic.getNumericSamples().add(numericSample1);
//		numericTopic.getNumericSamples().add(numericSample2);	
//		numericTopic.getNumericLifeCyleCol().add(numericLifecycle);

		session.save(numericTopic);
	    session.getTransaction().commit();
	    session.close();
	    HibernateUtil.getSessionFactory().close();
	}
	

	
	private static void testOneToMany_3(){
		//create test numeric topic
		Numeric numericTopic = new Numeric(); 
		numericTopic.setUnique_device_identifier("test_FIVOQQm6kjMY6arhvxJZ7CwzEX24BSi");
		numericTopic.setMetric_id("MDC_PULS_OXIM_PULS_RATE");
		numericTopic.setVendor_metric_id("vendor_metric_ID");
		numericTopic.setInstance_id(0);
		numericTopic.setUnit_id("unit_ID");
//		numericTopic.setInstance_handle(null);
		//create test numeric samples
		NumericSample numericSample1 = new NumericSample();
		Date d1 = new Date();
		numericSample1.setValue(60);
		numericSample1.setDevice_time(d1);
		numericSample1.setPresentation_time(d1);
		numericSample1.setSource_time(d1);
		numericSample1.setNumeric(numericTopic);
//		numericTopic.getNumericSamples().add(numericSample1);
		
		NumericSample numericSample2 = new NumericSample();
		Date d2 = new Date();
		numericSample2.setValue(70);
		numericSample2.setDevice_time(d2);
		numericSample2.setPresentation_time(d2);
		numericSample2.setSource_time(d2);
		numericSample2.setNumeric(numericTopic);
//		numericTopic.getNumericSamples().add(numericSample2);
		//create demo numeric lifecycle
		NumericLifeCycle numericLifecycle = new NumericLifeCycle();
		numericLifecycle.setAlive(1);
		numericLifecycle.setTime(new Date());
		
//		numericTopic.getNumericLifeCyleCol().add(numericLifecycle);
		//
		Session session = HibernateUtil.getSessionFactory().openSession();	 
		session.beginTransaction();
		session.save(numericTopic);
	    session.getTransaction().commit();
	    session.close();
	    HibernateUtil.getSessionFactory().close();
	}
	
	private static void basicInsert(){
	    Session session =  HibernateUtil.getSessionFactory().openSession();
	    session.beginTransaction();
	    VitalValues vv = new VitalValues("test_FIVOQQm6kjMY6arhvxJZ7CwzEX24BSi", "MDC_PULS_OXIM_PULS_RATE", 0, new Date(), 60);
	    session.save(vv);
	    session.getTransaction().commit();
	    session.close();
	    HibernateUtil.getSessionFactory().close();
	}

	
	private static void test_w_annotations(){
		VitalValues vv = new VitalValues("test_FIVOQQm6kjMY6arhvxJZ7CwzEX24BSi", "MDC_PULS_OXIM_PULS_RATE", 0, new Date(), 0);
		Manager mng = new Manager();
		vv = mng.createVitalValuesORA(vv);
		
		if(HibernateUtil.getSession().isOpen()){
			HibernateUtil.getSessionFactory().close();
			System.out.println("closed sessions");
		}
		
		System.out.println("generated ID "+vv.getId_vital_values());
		System.out.println("done");
	}
	
	
	private static void test_ValueORA_mappingfile(){
		VitalValuesDTO vitalValDto = new VitalValuesDTO();
		vitalValDto.setDevice_id("test_FIVOQQm6kjMY6arhvxJZ7CwzEX24BSi");
		vitalValDto.setMetric_id("MDC_PULS_OXIM_PULS_RATE");
		vitalValDto.setInstance_id(0);
		vitalValDto.setTime_tick(new Date());
		vitalValDto.setVital_value(60);
		
		Manager mng = new Manager();
		vitalValDto = mng.createVitalValues(vitalValDto);
		
		if(HibernateUtil.getSession().isOpen()){
			HibernateUtil.getSessionFactory().close();
			System.out.println("closed sessions");
		}
		
		System.out.println("generated ID "+vitalValDto.getId_vital_values());
		System.out.println("done");
	}
}
