//
//  ViewController.swift
//  DemoMap
//
//  Created by neusoft on 2018/6/5.
//  Copyright © 2018年 neusoft. All rights reserved.
//

import UIKit
import CoreLocation
import MapKit

class ViewController: UIViewController,CLLocationManagerDelegate{
    @IBOutlet weak var labelLatitude: UILabel!
    @IBOutlet weak var labelLontitude: UILabel!
    @IBOutlet weak var mapView: MKMapView!
    @IBOutlet weak var labelAddress: UILabel!
    
    var currentLocation:CLLocation!
    let locationManager = CLLocationManager()
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
        self.locationManager.delegate = self
        
        self.locationManager.desiredAccuracy = kCLLocationAccuracyBest
        self.locationManager.requestWhenInUseAuthorization()
        self.locationManager.startUpdatingLocation()
        
    }
    
    
    public func locationManager(_ manager: CLLocationManager, didUpdateLocations locations: [CLLocation]){
        currentLocation = locations.last
        if currentLocation.horizontalAccuracy > 0{
            labelLatitude.text = String(currentLocation.coordinate.latitude)
            labelLontitude.text = String(currentLocation.coordinate.longitude)
        }
        
        let geocoder: CLGeocoder = CLGeocoder()
        geocoder.reverseGeocodeLocation(currentLocation) {(placemark, error) -> Void in
            if(error == nil){
                let array = placemark! as NSArray
                let mark = array.firstObject as! CLPlacemark
                //所在国家
                let country = (mark.addressDictionary! as NSDictionary).value(forKey: "Country") as! String
                //省
                let state = (mark.addressDictionary! as NSDictionary).value(forKey: "State") as! String
                //区
                let sub = (mark.addressDictionary! as NSDictionary).value(forKey: "SubLocality") as! String
                //位置名称
                let name = (mark.addressDictionary! as NSDictionary).value(forKey: "Name") as! String
                
                print(country,state,sub,name)
                
                self.labelAddress.text = name
                self.locationManager.stopUpdatingLocation()
            }
            else{
                print("反定位错误")
            }
        }
    }

    
    public func locationManager(_ manager: CLLocationManager, didChangeAuthorization status: CLAuthorizationStatus){
        switch status {
        case .authorizedAlways,.authorizedWhenInUse:
            self.locationManager.startUpdatingLocation()
            mapView.showsUserLocation = true
        default:
            self.locationManager.stopUpdatingLocation()
            mapView.showsUserLocation = false
        }
    }
    
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
}

