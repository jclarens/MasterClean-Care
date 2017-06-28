<?php

namespace App\Http\Controllers;

use App\UserDocument;
use Illuminate\Http\Request;

class UserDocumentController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return UserDocument::all();
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

        $userDocument = UserDocument::create($data);

        return response()->json($userDocument, 201);
    }

    /**
     * Display the specified resource.
     *
     * @param  \App\UserDocument  $userDocument
     * @return \Illuminate\Http\Response
     */
    public function show(UserDocument $userDocument)
    {
        return $userDocument;
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \App\UserDocument  $userDocument
     * @return \Illuminate\Http\Response
     */
    public function edit(UserDocument $userDocument)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \App\UserDocument  $userDocument
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, UserDocument $userDocument)
    {
        $data = $request->all();

        if (array_key_exists('userId', $data)) {
            $userDocument->userId = $data['userId'];
        }
        if (array_key_exists('documentName', $data)) {
            $userDocument->documentName = $data['documentName'];
        }
        if (array_key_exists('documentPath', $data)) {
            $userDocument->documentPath = $data['documentPath'];
        }
        if (array_key_exists('documentType', $data)) {
            $userDocument->documentType = $data['documentType'];
        }
        if (array_key_exists('experience', $data)) {
            $userDocument->experience = $data['experience'];
        }

        $userDocument->save();

        return response()->json($userDocument, 200);
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \App\UserDocument  $userDocument
     * @return \Illuminate\Http\Response
     */
    public function destroy(UserDocument $userDocument)
    {
        $userDocument->delete();

        return reponse()->json('Deleted', 200);
    }
}
