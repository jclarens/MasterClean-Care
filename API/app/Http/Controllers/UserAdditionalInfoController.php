<?php

namespace App\Http\Controllers;

use App\UserAdditionalInfo;
use Illuminate\Http\Request;

class UserAdditionalInfoController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return UserAdditionalInfo::all();
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

        $userAdditionalInfo = UserAdditionalInfo::create($data);

        return response()->json($userAdditionalInfo, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\UserAdditionalInfo  $userAdditionalInfo
     * @return \Illuminate\Http\Response
     */
    public function show(UserAdditionalInfo $userAdditionalInfo)
    {
        return $userAdditionalInfo;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\UserAdditionalInfo  $userAdditionalInfo
     * @return \Illuminate\Http\Response
     */
    public function edit(UserAdditionalInfo $userAdditionalInfo)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\UserAdditionalInfo  $userAdditionalInfo
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, UserAdditionalInfo $userAdditionalInfo)
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
     * @param  \App\UserAdditionalInfo  $userAdditionalInfo
     * @return \Illuminate\Http\Response
     */
    public function destroy(UserAdditionalInfo $userAdditionalInfo)
    {
        $userAdditionalInfo->delete();

        return response()->json('Deleted', 200);
    }
}
