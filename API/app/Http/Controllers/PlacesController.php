<?php

namespace App\Http\Controllers;

use App\Places;
use Illuminate\Http\Request;

class PlacesController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return Places::all();
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        $data = $request->all();

        $places = Places::create($data);

        return response()->json($places, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\Places  $places
     * @return \Illuminate\Http\Response
     */
    public function show(Places $places)
    {
        return $places;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\Places  $places
     * @return \Illuminate\Http\Response
     */
    public function edit(Places $places)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Places  $places
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, Places $places)
    {
        $data = $request->all();

        if (array_key_exists('name', $data)) {
            $places->name = $data['name'];
        }
        if (array_key_exists('parent', $data)) {
            $places->parent = $data['parent'];
        }

        $places->save();

        return response()->json($places, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\Places  $places
     * @return \Illuminate\Http\Response
     */
    public function destroy(Places $places)
    {
        $places->delete();

        return response()->json('Deleted', 200);
    }

    /**
     * Search the specified resource from storage by parameter.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\Places  $places
     * @param  Parameter  $param
     * @param  Text  $text
     * @return \Illuminate\Http\Response
     */
    public function searchByParam(Request $request, Places $places, $param = 'name', $text)
    {
        return $language
            ->where($param,
                'like',
                '%'.$text.'%')
            ->get();
    }
}
