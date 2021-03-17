<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use Kreait\Firebase\Factory;

class FirebaseController extends Controller{

    function index(){
        return view('requests');
    }

    function table(){
        $database = app('firebase.database');
        $drinks = $database->getReference('Drinks')->getSnapshot()->getValue();

        foreach($drinks as $key => $drink) {
            $brand = $database->getReference('Brands')->getChild($drink['fk_brand'])->getChild('desc')->getSnapshot()->getValue();
            $auxDrink = $drink;
            $auxDrink['brand'] = $brand;
            $allDrinks[] = $auxDrink;
        }

        $view = \View::make('table', [
            'drinks' => $allDrinks
        ]);

        return $view->render();
    }

    function menuView(){
        return view('menu');
    }

    function addView(){
        $database = app('firebase.database');
        $brands = $database->getReference('Brands');
        return view('add', [
            'brands' => $brands->getValue()
        ]);
    }

    function editView($id){
        $database = app('firebase.database');
        $drink = $database->getReference('Drinks/' . $id)->getSnapshot();

        return view('edit', [
            'drink' => $drink->getValue(),
            'key' => $id
        ]);
    }

    function brandsView(){
        $database = app('firebase.database');
        $brands = $database->getReference('Brands')->getSnapshot();
        return view('brands', [
            'brands' => $brands->getValue()
        ]);
    }

    function couponsView(){
        $database = app('firebase.database');
        $coupons = $database->getReference('Coupons')->getSnapshot();
        return view('coupons', [
            'coupons' => $coupons->getValue()
        ]);
    }

    // CRUD FIREBASE

    function addDrink(Request $request){
        $database = app('firebase.database');
        $drink = $database->getReference('Drinks');
        $key = $drink->push()->getKey();

        $drink->getChild($key)->set([
            'id' => $key,
            'name' => $request->name,
            'price' => floatval($request->price),
            'fk_brand' => $request->brand
        ]);

        return redirect('/add');
    }

    function addBrand(Request $request){
        $database = app('firebase.database');
        $brand = $database->getReference('Brands');
        $key = $brand->push()->getKey();

        $brand->getChild($key)->set([
            'id' => $key,
            'desc' => $request->descBrand,
            'url' => 'https://path-to-image'
        ]);

        return redirect('brands');
    }

    function addCoupon(Request $request){
        $database = app('firebase.database');
        $coupon = $database->getReference('Coupons');
        $key = $coupon->push()->getKey();

        $minPrice = ($request->minPrice != '') ? floatval($request->minPrice) : 'none';

        $coupon->getChild($key)->set([
            'id' => $key,
            'name' => $request->name,
            'discount' => floatval($request->discount),
            'minPrice' => $minPrice
        ]);

        return redirect('coupons');
    }

    function editDrink(Request $request){
        $database = app('firebase.database');
        $drinks = $database->getReference('Drinks');

        $drinks->getChild($request->key)->set([
            "id" => $request->key,
            "name" => $request->name,
            "price" => floatval($request->price)
        ]);

        return redirect('/');
    }

    function deleteDrink($id){
        if(empty($id) || !isset($id)) return false;
        $database = app('firebase.database');

        if($database->getReference('Drinks')->getSnapshot()->hasChild($id)){
            $database->getReference('Drinks')->getChild($id)->remove();
        }

        $database = app('firebase.database');
        $drinks = $database->getReference('Drinks')->getSnapshot();

        $view = \View::make('table', [
            'drinks' => $drinks->getValue()
        ]);

        return $view->render();

    }
    
}
