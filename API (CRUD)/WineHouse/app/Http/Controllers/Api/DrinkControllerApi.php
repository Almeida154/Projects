<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use Illuminate\Http\Request;
use App\Drink;

class DrinkControllerApi extends Controller{
    
    public function listAll(){
        // return Drink::all();
        return json_encode(Drink::all());
    }

    public function singleDrink($id){
        $drink = Drink::find($id);
        return $drink;
    }

    public function add(Request $request){
        try {
            $drink = new Drink;
            $drink->product = $request->product;
            $drink->price = $request->price;
            $drink->save();
            return ['Status' => 'Success'];
        } catch(\Throwable $th) {
            return ['Status' => 'Error'];
        }
    }

    public function update(Request $request, $id){
        try {
            $drink = Drink::find($id);
            $drink->product = $request->product;
            $drink->price = $request->price;
            $drink->save();
            return ['Status' => 'Success'];
        } catch(\Throwable $th) {
            return ['Status' => 'Error'];
        }
    }

    public function delete($id){
        try {
            $drink = Drink::find($id);
            $drink->delete();
            return ['Status' => 'Success'];
        } catch(\Throwable $th) {
            return ['Status' => 'Error'];
        }
    }

}