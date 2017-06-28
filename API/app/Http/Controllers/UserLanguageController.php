<?php

namespace App\Http\Controllers;

use App\UserLanguage;
use Illuminate\Http\Request;

class UserLanguageController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return UserLanguage::all();
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

        $userLanguage = UserLanguage::create($data);

        return response()->json($userLanguage, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\UserLanguage  $userLanguage
     * @return \Illuminate\Http\Response
     */
    public function show(UserLanguage $userLanguage)
    {
        return $userLanguage;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\UserLanguage  $userLanguage
     * @return \Illuminate\Http\Response
     */
    public function edit(UserLanguage $userLanguage)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\UserLanguage  $userLanguage
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, UserLanguage $userLanguage)
    {
        $data = $request->all();

        if (array_key_exists('userId', $data)) {
            $userLanguage->userId = $data['userId'];
        }
        if (array_key_exists('languageId', $data)) {
            $userLanguage->languageId = $data['languageId'];
        }

        $userLanguage->save();

        return response()->json($userLanguage, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\UserLanguage  $userLanguage
     * @return \Illuminate\Http\Response
     */
    public function destroy(UserLanguage $userLanguage)
    {
        $userLanguage->delete();

        return response()->json('Deleted', 200);
    }
}
