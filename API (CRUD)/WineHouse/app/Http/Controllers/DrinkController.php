<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Drink;

class DrinkController extends Controller{

    // Views
    
    public function index(){
        $drinks = Drink::all();
        return view('welcome', [
            'drinks' => $drinks
        ]);
    }

    public function newDrink(){
        return view('add');
    }

    public function editDrink($id){
        $drink = Drink::find($id);
        return view('edit', [
            'drink' => $drink
        ]);
    }

    // Database Functions

    public function delete($id){
        try {
            $drink = Drink::find($id);
            $drink->delete();

            // Return a session, it makes call an alert from SweetAlert2 at views/message.blade.php
            return redirect('/')->with('success', 'Produto excluído com sucesso!');

        } catch(\Throwable $th) {
            return ['Error' => $th];
        }
    }

    public function update(Request $request){
        try {
            $drink = Drink::find($request->id);
            $drink->product = $request->product;
            $drink->price = $request->price;
            $drink->save();
            return redirect('/')->with('success', 'Produto atualizado com sucesso!');
        } catch(\Throwable $th) {
            return ['Error' => $th];
        }
    }

    public function add(Request $request){
        try {
            $drink = new Drink;
            $drink->product = $request->product;
            $drink->price = $request->price;
            $drink->save();
            return redirect('/')->with('success', 'Produto salvo com sucesso!');
        } catch(\Throwable $th) {
            return ['Error' => $th];
        }
    }
}